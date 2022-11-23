export function getAccessToken() {
  return localStorage.getItem("accessToken");
}

export function getRefreshToken() {
  return localStorage.getItem("refreshToken");
}

export function setAccessToken(token) {
  localStorage.setItem("accessToken", token);
}

export function setRefreshToken(token) {
  localStorage.setItem("refreshToken", token);
}

export function setTokens(tokens = {}) {
  if (tokens.accessToken && tokens.refreshToken && tokens.userType) {
    setAccessToken(tokens.accessToken);
    setRefreshToken(tokens.refreshToken);
    setUserTypeToken(tokens.userType);
  }
}

export function removeTokens() {
  localStorage.removeItem("accessToken");
  localStorage.removeItem("refreshToken");
  localStorage.removeItem("user-type");
}

export function setUserTypeToken(userType) {
  localStorage.setItem('user-type', userType);
}

export function getUserType() {
  return localStorage.getItem("user-type");
}

export function hasSessionData() {
  const accessToken = getAccessToken();

  if (accessToken === null || accessToken.length === 0)
    return false;

  const refreshToken = getRefreshToken();

  if (refreshToken === null || refreshToken.length === 0)
    return false;

  return true;
}
