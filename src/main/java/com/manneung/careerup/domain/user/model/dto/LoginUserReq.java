package com.manneung.careerup.domain.user.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "로그인을 위한 요청 객체")
public class LoginUserReq {

    @NotBlank(message = "회원의 이메일을 입력해주세요.")
    @ApiModelProperty(notes = "로그인 이메일을 입력해 주세요.")
    private String username;

    @NotBlank(message = "회원의 비밀번호를 입력해 주세요.")
    @ApiModelProperty(notes = "회원의 비밀번호를 입력해 주세요.")
    private String password;

}