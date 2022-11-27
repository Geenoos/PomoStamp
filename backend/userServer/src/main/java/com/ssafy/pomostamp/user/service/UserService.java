package com.ssafy.pomostamp.user.service;

import com.ssafy.pomostamp.user.dto.*;
import com.ssafy.pomostamp.user.exception.NotValidateAccessToken;
import com.ssafy.pomostamp.user.exception.NotValidateRefreshToken;
import java.util.List;

public interface UserService {
    // 개인 고유 auth로 회원가입 여부 확인
    List<User> checkAuth(String auth);

    // 카카오 회원가입 후 유저정보 반환
    UserResponse.UserInfo kakaoJoin(KakaoUserInfo userInfo, String kakaoRefreshToken);

    // 회원 블랙리스트 등록
    void registBlackList(String userId);

    // user_id 난수 생성
    User randomId(User user);


    // 탈퇴했던 유저이면 재가입 , 아니면 로그인하고 유저정보 반환
    UserResponse.UserInfo CheckBlackUser(KakaoUserInfo userInfo, String kakaoRefreshToken);

    // 로그인 시 카카오 리스레시 토큰 갱신
    UserResponse.UserInfo updateKakaoRefreshToken(String refreshToken, String userId);

    // 유저 닉네임 수정
    UserResponse.updateUserInfo updateUser(UserRequest.Create request);

    //리프레시 토큰 생성
    String refreshToken(String userId);

    // 토큰 재발급
    TokenResponse.NewToken getNewToken(TokenRequest.Create request, String refreshToken) throws NotValidateRefreshToken, NotValidateAccessToken;

    // 토큰으로 유저 찾기
    UserInfo findUserByToken(String accessToken) throws NotValidateAccessToken;





}
