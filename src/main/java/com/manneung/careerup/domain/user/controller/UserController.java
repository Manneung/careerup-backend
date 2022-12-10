package com.manneung.careerup.domain.user.controller;


import com.manneung.careerup.domain.base.BaseResponse;
import com.manneung.careerup.domain.user.model.dto.GoogleLoginReq;

import com.manneung.careerup.domain.user.model.dto.LoginUserRes;
import com.manneung.careerup.domain.user.model.dto.NaverLoginReq;
import com.manneung.careerup.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.io.IOException;

import static com.manneung.careerup.domain.base.BaseResponseStatus.USER_FAILED_TO_LOG_IN_ERROR;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    //소셜로그인 api -> 실행여부는 아직 모름..!
    @PostMapping("/google-login")
    public BaseResponse<LoginUserRes> googleLogin(@Valid @RequestBody GoogleLoginReq googleLoginReq){
        //googleLoginReq: 구글 idToken

        //loginUserRes: 접근토큰과 리프레시토큰 리턴
        LoginUserRes loginUserRes = userService.authGoogleUser(googleLoginReq);

        if(loginUserRes == null){ //유효하지 않은 토큰
            return new BaseResponse<>(USER_FAILED_TO_LOG_IN_ERROR);
        }


        return new BaseResponse<>(loginUserRes); //토큰 정보 리턴
    }

    @PostMapping("/naverLogin")
    public BaseResponse<LoginUserRes> naverLogin(@Valid @RequestBody NaverLoginReq naverLoginReq) throws IOException {
        //naverLoginReq: 네이버 access token 담겨있음

        //loginUserRes: 접근토큰과 리프레시토큰 리턴
        LoginUserRes loginUserRes = userService.authNaverUser(naverLoginReq);

        if(loginUserRes == null){ //유효하지 않은 토큰
            return new BaseResponse<>(USER_FAILED_TO_LOG_IN_ERROR);
        }

        return new BaseResponse<>(loginUserRes); //토큰 정보 리턴
    }






}
