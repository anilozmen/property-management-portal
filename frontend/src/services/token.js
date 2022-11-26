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
    setFullName(tokens.fullName);
    setEmail(tokens.email);
  }
}

export function removeTokens() {
  localStorage.removeItem("accessToken");
  localStorage.removeItem("refreshToken");
  localStorage.removeItem("user-type");
  localStorage.removeItem("fullName");
  localStorage.removeItem("email");
}

export function setUserTypeToken(userType) {
  localStorage.setItem('user-type', userType);
}

export function setFullName(fullName) {
  localStorage.setItem('fullName', fullName);
}

export function setEmail(email) {
  localStorage.setItem('email', email);
}

export function getUserType() {
  return localStorage.getItem("user-type");
}

export function getFullName() {
  return localStorage.getItem('fullName');
}

export function getEmail() {
  return localStorage.getItem('email');
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
