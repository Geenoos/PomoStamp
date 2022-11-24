import React from "react";
import { Box } from "@mui/material";
import { useSearchParams } from "react-router-dom";
import { kakaoLogin, refresh } from "../apis/login";
import { Navigate } from "react-router-dom";
import { loginState, userState } from "../store";
import { useRecoilState, useSetRecoilState } from "recoil";
import { api } from "../apis";

// jwt expiry time - 1 hour (ms)
const JWT_EXPIRY_TIME = 3600 * 1000;

function OAuthRedirectPage() {
  const [searchParams] = useSearchParams();
  const code = searchParams.get("code");

  const [login, setLogin] = useRecoilState(loginState);
  const setUser = useSetRecoilState(userState);

  const onLoginSuccess = (res) => {
    const { accessToken, userId, nickname, pomoTime } = res.data.user;
    // api.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
    localStorage.setItem("accessToken", accessToken);
    setLogin(true);
    setUser({ userId, nickname, pomoTime });
    onSilentRefresh(accessToken).then(onRefreshSuccess).catch(onLoginFailed);
  };

  const onRefreshSuccess = (res) => {
    if (!login) return;
    const { accessToken } = res.data;
    // api.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
    localStorage.setItem("accessToken", accessToken);
    onSilentRefresh(accessToken).then(onRefreshSuccess).catch(onRefreshFailed);
  };

  const onLoginFailed = (err) => {
    console.error(err);
    // error 창 추가?
  };

  const onRefreshFailed = (err) => {
    console.error(err);
    setLogin(false);
    setUser(null);
    localStorage.removeItem("accessToken");
    // api.defaults.headers.common["Authorization"] = undefined;
    // error 창 추가?
  };

  const onSilentRefresh = (accessToken) => {
    return new Promise((resolve, reject) =>
      setTimeout(() => {
        refresh(accessToken).then(resolve).catch(reject);
      }, JWT_EXPIRY_TIME - 60000)
    );
  };

  kakaoLogin(code).then(onLoginSuccess).catch(onLoginFailed);

  return (
    <React.Fragment>
      <Box>Logging in...</Box>
      {login && <Navigate to="/" />}
    </React.Fragment>
  );
}

export default OAuthRedirectPage;
