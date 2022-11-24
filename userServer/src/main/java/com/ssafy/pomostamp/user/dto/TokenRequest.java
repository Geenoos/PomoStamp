package com.ssafy.pomostamp.user.dto;

import lombok.*;

public class TokenRequest {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Create{
        private String accessToken;
    }
}
