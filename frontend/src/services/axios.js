import axios from "axios";
import { getAccessToken, getRefreshToken, removeTokens, setTokens } from "./token";

const API_BASE_URL = "http://localhost:8080/api/v1";

axios.defaults.baseURL = API_BASE_URL;
axios.defaults.headers.post["Content-Type"] = "application/json";
axios.defaults.headers.post["Accept"] = "application/json";
axios.defaults.headers.get["Accept"] = "application/json";

let isTokenBeingRefreshed = false;
let heldRequests = [];

axios.interceptors.request.use(
  (config) => {
    if (getAccessToken())
      config.headers.common["Authorization"] = "Bearer " + getAccessToken();

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

axios.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    const newRequest = error.config;

    if (
      error.response &&
      error.response.status === 401 &&
      error.response.data.msg === 'TOKEN_EXPIRED'
    ) {
      if (!getRefreshToken()) {
        removeTokens();

        return Promise.reject(error);
      }

      if (isTokenBeingRefreshed) {
        return new Promise((resolve, reject) => {
          heldRequests.push({ resolve, reject });
        })
          .then((newToken) => {
            newRequest.headers.authorization = 'Bearer ' + newToken;

            return axios(newRequest);
          })
          .catch((err) => {
            return Promise.reject(err);
          });
      }

      isTokenBeingRefreshed = true;

      return axios.post(
        API_BASE_URL + '/refresh',
        {
          accessToken: getAccessToken(),
          refreshToken: getRefreshToken(),
        }
      )
        .then((res) => {
          isTokenBeingRefreshed = false;
          setTokens({
            accessToken: res.data.accessToken,
            refreshToken: res.data.refreshToken
          });
          newRequest.headers.authorization = 'Bearer' + res.data.accessToken;
          releaseHeldRequests(null, res.data.accessToken);

          return axios(newRequest);
        })
        .catch((err) => {
          if (err.response.status === 401) {
            removeTokens();

            return Promise.reject(err);
          }
          releaseHeldRequests(err, null);
        });
    } else {
      return Promise.reject(error);
    }
  }
);

function releaseHeldRequests(err, refreshedAccessToken = null) {
  heldRequests.forEach((elementPromise) => {
    if (err) {
      return elementPromise.reject(err);
    }

    elementPromise.resolve(refreshedAccessToken);
  });

  heldRequests = [];
}
