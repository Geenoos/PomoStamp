import MainPage from "./pages/MainPage";
import OAuthRedirectPage from "./pages/OAuthRedirectPage";
import GlobalLayout from "./pages/_layout";
import UserPage from "./pages/UserPage";
import PrivateRoute from "./PrivateRoute";
import OpenViduPage from "./components/openvidu";

export const routes = [
  {
    path: "/",
    element: <GlobalLayout />,
    children: [
      { path: "/", element: <MainPage />, index: true },
      { path: "/oauth", element: <OAuthRedirectPage />, index: true },
      {
        path: "/mypage",
        element: (
          <PrivateRoute>
            <UserPage />
          </PrivateRoute>
        ),
        index: true,
      },
      {
        path: "/studyroom/:roomId",
        element: (
          <PrivateRoute>
            <OpenViduPage />
          </PrivateRoute>
        ),
        index: true,
      },
    ],
  },
];

export const pages = [
  { route: "/" },
  { route: "/oauth" },
  { route: "/userpage" },
];
