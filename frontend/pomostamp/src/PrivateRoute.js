import React from "react";
import { Navigate, Route } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { loginState } from "./store";

function PrivateRoute({ children }) {
  const login = useRecoilValue(loginState);
  return login ? (
    <>{children}</>
  ) : (
    <Navigate
      to={{
        pathname: "/",
      }}
    />
  );
}

export default PrivateRoute;
