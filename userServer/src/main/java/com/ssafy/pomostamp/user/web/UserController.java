package com.ssafy.pomostamp.user.web;

import com.ssafy.pomostamp.user.dto.*;
import com.ssafy.pomostamp.user.exception.NotValidateAccessToken;
import com.ssafy.pomostamp.user.exception.NotValidateRefreshToken;
import com.ssafy.pomostamp.user.service.KakaoAuthService;
import com.ssafy.pomostamp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pomo/v1/user")
@CrossOrigin(origins = "https://i7a608.p.ssafy.io")
public class UserController {

    @Autowired
    private KakaoAuthService kakaoAuthService;

    private final UserService userService;

    @GetMapping("/kakao")
    public ResponseEntity<Object> kakaoLogin(String code, HttpServletResponse res)  {
        System.out.println(code);
        try {
            KakaoTokenInfo kakaoTokenInfo = kakaoAuthService.sendCode(code);
            KakaoUserInfo kakaoUserInfo = kakaoAuthService.sendToken(kakaoTokenInfo.getAccessToken());
            List<User> user = userService.checkAuth(Long.toString(kakaoUserInfo.getId()));
            HashMap<String,Object> hashMap = new HashMap<>();
            // 이미 가입 한 경우 블랙리스트 확인해서 탈퇴한 회원인지 확인
            if(!user.isEmpty()){
                // 카카오로 부터 받은 유저의 auth로 조회하여 블랙리스트의 user-id와 겹치지 않는 user의 user_id 존재하는지 확인
                // user-id 존재하면 로그인 아니면 재가입 시킨 후 유저정보 반환
                UserResponse.UserInfo response = userService.CheckBlackUser(kakaoUserInfo, kakaoTokenInfo.getRefreshToken());
                hashMap.put("user", response);
                String refreshToken = userService.refreshToken(response.getUserId());
                res.addHeader("Set-Cookie", "refreshToken="+refreshToken+"; path=/; MaxAge=7 * 24 * 60 * 60; SameSite=Lax; HttpOnly");
                return ResponseEntity.status(HttpStatus.OK).body(hashMap);
            }
            // 아직 가입 안 한 경우
            else{
                // 유저 회원 가입 후 유저 정보 반환
                UserResponse.UserInfo response = userService.kakaoJoin(kakaoUserInfo, kakaoTokenInfo.getRefreshToken());
                hashMap.put("user", response);
                String refreshToken = userService.refreshToken(response.getUserId());
                res.addHeader("Set-Cookie", "refreshToken="+refreshToken+"; path=/; MaxAge=7 * 24 * 60 * 60; SameSite=Lax; HttpOnly");
                return ResponseEntity.status(HttpStatus.OK).body(hashMap);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("")
    public ResponseEntity<?> delete(String userId){
        //카카오 유저
        try {
            String accessToken = kakaoAuthService.refreshToken(userId);
            kakaoAuthService.kakaoUnlink(accessToken);
            userService.registBlackList(userId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponse.updateUserInfo> updateUser(@RequestBody UserRequest.Create request){
        UserResponse.updateUserInfo response = userService.updateUser(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/retoken")
    public ResponseEntity<TokenResponse.NewToken> reIssue(@RequestBody TokenRequest.Create request, HttpServletResponse res, @CookieValue(name="refreshToken") String refresh) throws NotValidateAccessToken, NotValidateRefreshToken {
        TokenResponse.NewToken response = userService.getNewToken(request, refresh);
        String refreshToken = userService.refreshToken(response.getUserId());
        res.addHeader("Set-Cookie", "refreshToken="+refreshToken+"; path=/; MaxAge=7 * 24 * 60 * 60; SameSite=Lax; HttpOnly");
       return ResponseEntity.ok().body(response);
    }

}