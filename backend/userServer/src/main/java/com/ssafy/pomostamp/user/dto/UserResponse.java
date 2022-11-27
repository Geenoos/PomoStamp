package com.ssafy.pomostamp.user.dto;

import lombok.*;

public class UserResponse {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UserInfo {
        private String userId;
        private String nickname;
        private int pomoTime;
        private String accessToken;
        public static UserInfo build(com.ssafy.pomostamp.user.dto.UserInfo userInfo, String accessToken) {
            return UserInfo.builder()
                    .userId(userInfo.getUser().getUserId())
                    .nickname(userInfo.getNickname())
                    .pomoTime(userInfo.getPomoTime())
                    .accessToken(accessToken)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class updateUserInfo {
        private String userId;
        private String nickname;
        public static updateUserInfo build(com.ssafy.pomostamp.user.dto.UserInfo userInfo) {
            return updateUserInfo.builder()
                    .userId(userInfo.getUser().getUserId())
                    .nickname(userInfo.getNickname())
                    .build();
        }
    }

}
