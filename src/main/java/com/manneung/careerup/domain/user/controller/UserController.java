package com.manneung.careerup.domain.user.controller;


import com.manneung.careerup.domain.base.BaseResponse;
import com.manneung.careerup.domain.user.model.GoogleLoginReq;

import com.manneung.careerup.domain.user.model.LoginUserRes;
import com.manneung.careerup.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/google-login")
    public BaseResponse<LoginUserRes> googleLogin(@RequestBody GoogleLoginReq googleLoginReq){
        LoginUserRes loginUserRes = userService.authGoogleUser(googleLoginReq);


        return new BaseResponse<>(loginUserRes);
    }

//    @ApiOperation(value = "구글 로그인", notes = "구글 로그인을 합니다.")
//    @PostMapping("/googleLogin")
//    public ResponseEntity<ResponseDto<LoginResponse>> googleLogin(@Valid @RequestBody GoogleLoginRequest googleLoginRequest){
//        return ResponseEntity.ok(ResponseDto.create(EUserResponseMessage.GOOGLELOGIN_SUCCESS.getMessage(), this.userService.authGoogleUser(googleLoginRequest)));
//    }
//
//    @ApiOperation(value = "네이버 로그인", notes = "네이버 로그인을 합니다.")
//    @PostMapping("/naverLogin")
//    public ResponseEntity<ResponseDto<LoginResponse>> naverLogin(@Valid @RequestBody NaverLoginRequest naverLoginRequest) throws IOException {
//        return ResponseEntity.ok(ResponseDto.create(EUserResponseMessage.NAVERLOGIN_SUCCES.getMessage(), this.userService.authNaverUser(naverLoginRequest)));
//    }
//
//    @PostMapping("/testLogin")
//    public ResponseEntity<ResponseDto<LoginResponse>> testLogin(@RequestBody LoginRequest loginRequest) {
//        LoginResponse loginResponse = this.userService.loginUser(loginRequest);
//        return ResponseEntity.ok(ResponseDto.create(EUserResponseMessage.LOGIN_SUCCESS.getMessage(), loginResponse));
//    }

}
