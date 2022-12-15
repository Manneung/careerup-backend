package com.manneung.careerup.domain.user.controller;


import com.manneung.careerup.domain.base.BaseResponse;
import com.manneung.careerup.domain.user.model.dto.GoogleLoginReq;

import com.manneung.careerup.domain.user.model.dto.LoginUserReq;
import com.manneung.careerup.domain.user.model.dto.LoginUserRes;
import com.manneung.careerup.domain.user.model.dto.NaverLoginReq;
import com.manneung.careerup.domain.user.service.UserService;
import com.manneung.careerup.test.TestReq;
import com.manneung.careerup.test.TestRes;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.io.IOException;

import static com.manneung.careerup.domain.base.BaseResponseStatus.SUCCESS;
import static com.manneung.careerup.domain.base.BaseResponseStatus.USER_FAILED_TO_LOG_IN_ERROR;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "구글 로그인", notes = "구글 로그인")
    @PostMapping("/google-login")
    public ResponseEntity<BaseResponse<LoginUserRes>> googleLogin(@Valid @RequestBody GoogleLoginReq googleLoginReq){
        //googleLoginReq: 구글 idToken

        //loginUserRes: 접근토큰과 리프레시토큰 리턴
        LoginUserRes loginUserRes = userService.authGoogleUser(googleLoginReq);

        if(loginUserRes == null){ //유효하지 않은 토큰
            return ResponseEntity.ok(BaseResponse.create(USER_FAILED_TO_LOG_IN_ERROR));
        }


        return ResponseEntity.ok(BaseResponse.create(SUCCESS, loginUserRes));
        //토큰 정보 리턴
    }


    @ApiOperation(value = "네이버 로그인", notes = "네이버 로그인")
    @PostMapping("/naver-login")
    public ResponseEntity<BaseResponse<LoginUserRes>> naverLogin(@Valid @RequestBody NaverLoginReq naverLoginReq)
                                                                                                    throws IOException {
        //naverLoginReq: 네이버 access token 담겨있음

        //loginUserRes: 접근토큰과 리프레시토큰 리턴
        LoginUserRes loginUserRes = userService.authNaverUser(naverLoginReq);

        if(loginUserRes == null){ //유효하지 않은 토큰
            return ResponseEntity.ok(BaseResponse.create(USER_FAILED_TO_LOG_IN_ERROR));
        }

        return ResponseEntity.ok(BaseResponse.create(SUCCESS, loginUserRes)); //토큰 정보 리턴
    }


    @ApiOperation(value = "테스트 로그인", notes = "테스트 로그인(아이디, 비밀번호 필요)")
    @PostMapping("/test-login")
    public ResponseEntity<BaseResponse<LoginUserRes>> testLogin(@RequestBody LoginUserReq loginUserReq) {
        LoginUserRes loginUserRes = userService.loginUser(loginUserReq);

        return ResponseEntity.ok(BaseResponse.create(SUCCESS, loginUserRes));

        //return ResponseEntity.ok(ResponseDto.create(EUserResponseMessage.LOGIN_SUCCESS.getMessage(), loginResponse));
    }



}
