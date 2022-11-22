import axios from "axios";
import { getAccessToken, getRefreshToken, removeTokens, setTokens } from "./token";

const API_BASE_URL = "http://localhost:8080/api/v1";

const http = {
  get: function (url, params = {}) {
    return axios.get(
      API_BASE_URL + url,
      {
        params,
        headers: {
          "Authorization": "Bearer " + getAccessToken(),
          "Accept": "application/json"
        }
      }
    )
      .then(response => response.data);
  },

  post: function (url, body = {}) {
    return axios.post(
      API_BASE_URL + url,
      body,
      {
        headers: {
          "Authorization": "Bearer " + getAccessToken(),
          "Content-Type": "application/json",
          "Accept": "application/json"
        }
      }
    )
      .then(response => response.data);
  },

  put: function (url, body = {}) {
    return axios.put(
      API_BASE_URL + url,
      body,
      {
        headers: {
          "Authorization": "Bearer " + getAccessToken(),
          "Content-Type": "application/json",
          "Accept": "application/json"
        }
      }
    )
      .then(response => response.data);
  },

  delete: function (url) {
    return axios.delete(
      API_BASE_URL + url,
      {
        headers: {
          "Authorization": "Bearer " + getAccessToken(),
          "Accept": "application/json"
        }
      }
    )
      .then(response => response.data);
  }
};

let isTokenBeingRefreshed = false;
let heldRequests = [];

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

      return http.post(
        API_BASE_URL + '/refresh',
        {
          accessToken: token.getAccessToken(),
          refreshToken: token.getRefreshToken(),
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

export default http;
