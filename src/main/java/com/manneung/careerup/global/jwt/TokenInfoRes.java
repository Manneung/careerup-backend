package com.manneung.careerup.global.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TokenInfoRes {
    //사용자가 갖고 있는 권한, 접근 토큰, 리프레시토큰, 리프레시토큰 만료 시간
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long refreshTokenExpirationTime;

    public static TokenInfoRes from(
            String grantType, String accessToken, String refreshToken, Long refreshTokenExpirationTime) {
        return TokenInfoRes.builder()
                .grantType(grantType)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(refreshTokenExpirationTime)
                .build();
    }
}