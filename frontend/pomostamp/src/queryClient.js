import { QueryClient } from "@tanstack/react-query";
import axios from "axios";

export const getClient = (() => {
  let client = null;
  return () => {
    if (!client)
      client = new QueryClient({
        defaultOptions: {
          queries: {
            cacheTime: 0,
            staleTime: 1000 * 60 * 5,
            refetchOnMount: false,
            refetchOnReconnect: false,
            refetchOnWindowFocus: false,
            retury: false,
          },
        },
      });
    return client;
  };
})();
const BASE_URL = "";
const SERVER_URL = `${process.env.REACT_APP_END_POINT}/pomo/v1`;

export const restAxios = async ({ method, path, body, params }) => {
  try {
    let url = `${SERVER_URL}${path}`;
    const axiosOptions = {
      method,
      headers: {
        withCredentials: true,
        "Access-Control-Allow-Origin": process.env.REACT_APP_END_POINT,
      },
    };
    if (params) {
      const searchParams = new URLSearchParams(params);
      url += "?" + searchParams.toString();
    }
    if (body) axiosOptions.body = JSON.stringify(body);

    const res = await axios(url, axiosOptions).then((res) => {
      return res;
    });
    const json = await res.json();
    return json;
  } catch (err) {
    console.error(err.response);
  }
};

export const restFetcher = async ({ method, path, body, params }) => {
  try {
    let url = `${SERVER_URL}${path}`;
    const fetchOptions = {
      method,
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": BASE_URL,
      },
    };
    if (params) {
      const searchParams = new URLSearchParams(params);
      url += "?" + searchParams.toString();
    }

    if (body) fetchOptions.body = JSON.stringify(body);

    const res = await fetch(url, fetchOptions);
    const json = await res.json();
    return json;
  } catch (err) {
    console.error(err);
  }
};

export const QueryKeys = {
  TODOLIST: "TODOLIST",
  POMOTIME: "POMOTIME",
  IMAGE: "IMAGE",
  STUDYROOM: "STUDYROOM",
};
