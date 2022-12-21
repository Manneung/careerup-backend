package com.manneung.careerup.domain.user.model.dto;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "로그인을 위한 응답 객체")
public class LoginUserRes {

    private String accessToken;
//    private String refreshToken;
    private String email;
    //응답 토큰을 기반으로 접근토큰과 리프레시토큰 리턴


//    public static LoginUserRes from(TokenInfoRes tokenInfoRes, String email) {
//
//        return LoginUserRes.builder()
//                .accessToken(tokenInfoRes.getAccessToken())
//                .refreshToken(tokenInfoRes.getRefreshToken())
//                .email(email)
//                .build();
//    }

}