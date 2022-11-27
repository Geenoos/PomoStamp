package com.ssafy.pomostamp.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.pomostamp.user.dto.KakaoTokenInfo;
import com.ssafy.pomostamp.user.dto.KakaoUserInfo;
import com.ssafy.pomostamp.user.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class KakaoAuthServiceImpl implements KakaoAuthService{


    private final UserInfoRepository userInfoRepository;

    // 카카오 서버로 인증코드 보내서 액세스/리프레시 토큰을 받는 메소드
    @Override
    public KakaoTokenInfo sendCode(String code) throws Exception {
        String reqUrl = "https://kauth.kakao.com/oauth/token";
        KakaoTokenInfo kakaoTokenInfo = null;

        URL url = new URL(reqUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        StringBuilder sb = new StringBuilder();
        sb.append("grant_type=authorization_code");
        sb.append("&client_id=124aa8ad76bbb61b389b47279b4453bf");
        sb.append("&redirect_uri=https://i7a608.p.ssafy.io/oauth");
//        sb.append("&redirect_uri=http://localhost:8080");
        sb.append("&code=" + code);
        bw.write(sb.toString());
        bw.flush();

        int responseCode = conn.getResponseCode();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }

        ObjectMapper om = new ObjectMapper();

        kakaoTokenInfo = om.readValue(result, KakaoTokenInfo.class);

        System.out.println(kakaoTokenInfo.toString());

        return kakaoTokenInfo;
    }

    //카카오 서버에 액세스 토큰을 보내서 사용자 정보를 받아오는 메서드
    @Override
    public KakaoUserInfo sendToken(String accessToken) throws Exception {
        String reqUrl = "https://kapi.kakao.com/v2/user/me";

        URL url = new URL(reqUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);

        int responseCode = conn.getResponseCode();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }

        ObjectMapper om = new ObjectMapper();

        KakaoUserInfo kakaoUserInfo = om.readValue(result, KakaoUserInfo.class);


        JSONObject jObject = new JSONObject(result);
        JSONObject properties = jObject.getJSONObject("properties");

        kakaoUserInfo.setNickname(properties.getString("nickname"));

        return kakaoUserInfo;
    }



    @Override
    public String refreshToken(String userId) throws Exception {
        String reqUrl = "https://kauth.kakao.com/oauth/token";
        String refreshToken = userInfoRepository.findByUserId(userId).getKakaoRefreshToken();
        URL url = new URL(reqUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        StringBuilder sb = new StringBuilder();
        sb.append("grant_type=refresh_token");
        sb.append("&client_id=124aa8ad76bbb61b389b47279b4453bf");
        sb.append("&refresh_token=" + refreshToken);
        bw.write(sb.toString());
        bw.flush();

        int responseCode = conn.getResponseCode();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }

        ObjectMapper om = new ObjectMapper();

        KakaoTokenInfo refreshedTokenInfo = om.readValue(result, KakaoTokenInfo.class);

        return refreshedTokenInfo.getAccessToken();

    }

    @Override
    public void kakaoUnlink(String accessToken) throws Exception {
        String reqUrl = "https://kapi.kakao.com/v1/user/unlink";

        URL url = new URL(reqUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);

        int responseCode = conn.getResponseCode();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }

    }
}
