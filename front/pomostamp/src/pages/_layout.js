import React, { Suspense } from "react";
import { Outlet } from "react-router-dom";

const Layout = () => {
  return (
    <div>
      <Suspense fallback={"loading..."}>
        <Outlet />
      </Suspense>
    </div>
  );
};

export default Layout;
