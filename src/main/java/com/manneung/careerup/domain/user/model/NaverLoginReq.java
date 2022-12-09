package com.manneung.careerup.domain.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@ApiModel(description = "네이버로그인을 위한 요청 객체")
@NoArgsConstructor
public class NaverLoginReq {
    //@NotBlank(message = "인증 jwt 토큰")
    @ApiModelProperty(notes = "네이버 accessToken을 주세요.")
    private String token;

    public void setToken(String token) {
        this.token = token;
    }
}