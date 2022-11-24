package com.ssafy.pomostamp.user.service;

import com.ssafy.pomostamp.user.dto.*;
import com.ssafy.pomostamp.user.exception.NotValidateAccessToken;
import com.ssafy.pomostamp.user.exception.NotValidateRefreshToken;
import com.ssafy.pomostamp.user.repository.BlackListRepository;
import com.ssafy.pomostamp.user.repository.UserInfoRepository;
import com.ssafy.pomostamp.user.repository.UserRepository;
import com.ssafy.pomostamp.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements  UserService{

    private final UserRepository userRepository;

    private final JWTUtil jwtUtil;

    private final UserInfoRepository userInfoRepository;

    private final BlackListRepository blackListRepository;
    @Override
    public List<User> checkAuth(String auth) {
        return userRepository.findByAuth(auth);
    }

    @Transactional
    @Override
    public UserResponse.UserInfo kakaoJoin(KakaoUserInfo kakaouserInfo, String kakaoRefreshToken) {
        User user = User.kakaoUserCreate(kakaouserInfo);
        user = this.randomId(user);
        userRepository.save(user);
        User savedUser = userRepository.findByUserId(user.getUserId());
        UserInfo userInfo = UserInfo.kakaoUserInfoCreate(kakaouserInfo, savedUser, kakaoRefreshToken );
        userInfoRepository.save(userInfo);
        String accessToken = jwtUtil.createToken(savedUser.getUserId());
        return  UserResponse.UserInfo.build(userInfo, accessToken);
    }

    @Override
    public void registBlackList(String userId) {
        User user = userRepository.findByUserId(userId);
        System.out.println(user);
        BlackList blackList = BlackList.deleteUser(user);
        blackListRepository.save(blackList);
    }

    @Override
    public User randomId(User user) {
        while(true){
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < 16; i++){
                if(random.nextBoolean()) {
                    sb.append((char)((int)(random.nextInt(26))+65));
                }else{
                    sb.append((random.nextInt(10)));
                }
            }
            User duple = userRepository.findByUserId(sb.toString());
            if(duple == null){
                user.setUserId(sb.toString());
                break;
            }
        }
        return user;
    }

    @Override
    public UserResponse.UserInfo CheckBlackUser(KakaoUserInfo userInfo, String kakaoRefreshToken) {
        String userId = userRepository.notExistsBlackListUserId(Long.toString(userInfo.getId()));
        System.out.println(userId);
        if(userId != null){ // 로그인 시킨다.
            return this.updateKakaoRefreshToken(kakaoRefreshToken, userId);
        }else{ // 재가입 시킨다.
            return this.kakaoJoin(userInfo, kakaoRefreshToken);
        }
    }

    @Override
    public UserResponse.UserInfo updateKakaoRefreshToken(String refreshToken, String userId) {
        UserInfo userInfo = userInfoRepository.findByUserId(userId);
        userInfo.setKakaoRefreshToken(refreshToken);
        userInfoRepository.save(userInfo);
        String accessToken = jwtUtil.createToken(userId);
        return UserResponse.UserInfo.build(userInfo, accessToken);
    }

    @Override
    public UserResponse.updateUserInfo updateUser(UserRequest.Create request) {
        UserInfo userInfo = userInfoRepository.findByUserId(request.getUserId());
        userInfo.setNickname(request.getNickname());
        UserInfo savedUserInfo = userInfoRepository.save(userInfo);
        return UserResponse.updateUserInfo.build(savedUserInfo);
    }

    @Override
    public String refreshToken(String userId) {
        String refreshToken = jwtUtil.createRefreshToken();
        UserInfo userInfo = userInfoRepository.findByUserId(userId);
        userInfo.setJwtRefreshToken(refreshToken);
        userInfoRepository.save(userInfo);
        return refreshToken;
    }

    @Override
    public TokenResponse.NewToken getNewToken(TokenRequest.Create request, String refreshToken) throws NotValidateRefreshToken, NotValidateAccessToken {
       if(!jwtUtil.validateTokenExpiration(refreshToken)){
           throw new NotValidateRefreshToken();
       }
       UserInfo userInfo = this.findUserByToken(request.getAccessToken());
       if(userInfo == null){
           throw new NotValidateAccessToken();
       }
       if(!userInfo.getJwtRefreshToken().equals(refreshToken)){
           throw new NotValidateRefreshToken();
       }
       String accessToken = jwtUtil.createToken(userInfo.getUserId());
       return TokenResponse.NewToken.build(accessToken, userInfo.getUserId() );
    }

    @Override
    public UserInfo findUserByToken(String accessToken) throws NotValidateAccessToken {
        String userId = jwtUtil.getUserId(accessToken);
        return userInfoRepository.findByUserId(userId);
    }


}
