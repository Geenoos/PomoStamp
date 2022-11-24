import { request } from ".";

const kakaoLogin = async (code) => request(`/user/kakao?code=${code}`, "get");

const refresh = (accessToken) =>
  request("/user/retoken", "post", { accessToken });

export { kakaoLogin, refresh };
