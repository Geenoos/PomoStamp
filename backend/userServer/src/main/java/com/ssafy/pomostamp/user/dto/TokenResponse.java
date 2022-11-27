package com.ssafy.pomostamp.user.dto;

import lombok.*;

public class TokenResponse {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class NewToken {
        private String userId;
        private String accessToken;

        public static TokenResponse.NewToken build(String accessToken, String userId) {
            return TokenResponse.NewToken.builder()
                    .userId(userId)
                    .accessToken(accessToken)
                    .build();
        }
    }
}
