import { useQuery } from "@tanstack/react-query";
import axios from "axios";

export const api = axios.create({
  baseURL: `${process.env.REACT_APP_END_POINT}/pomo/v1`,
  headers: {
    withCredentials: true,
    "Access-Control-Allow-Origin": process.env.REACT_APP_END_POINT,
  },
});

export const request = (url, method, body) => {
  const token = localStorage.getItem("accessToken");
  return api({
    method,
    url,
    data: body,
    headers: { Authorization: `Bearer ${token}` },
  });
};
export const requestImage = (url, method, body) => {
  const token = localStorage.getItem("accessToken");
  return api({
    method,
    url,
    data: body,
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "multipart/form-data",
    },
  });
};

export function useGetRequest(url) {
  return useQuery([url], () => request(url, "get").then((res) => res.data));
}

export function usePutRequest(url, body) {
  return useQuery([url], () =>
    request(url, "put", body).then((res) => res.data)
  );
}
