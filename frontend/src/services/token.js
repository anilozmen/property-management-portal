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
  if (tokens.accessToken && tokens.refreshToken) {
    setAccessToken(tokens.accessToken);
    setRefreshToken(tokens.refreshToken);
  }
}

export function removeTokens() {
  localStorage.removeItem("accessToken");
  localStorage.removeItem("refreshToken");
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
