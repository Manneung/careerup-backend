package com.manneung.careerup.domain.user.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@ApiModel(description = "구글로그인을 위한 요청 객체")
@NoArgsConstructor
public class GoogleLoginReq {
    //@NotBlank(message = "인증 jwt 토큰")
    @ApiModelProperty(notes = "구글 idToken을 주세요.")
    private String token;

    public void setToken(String token) {
        this.token = token;
    }
}