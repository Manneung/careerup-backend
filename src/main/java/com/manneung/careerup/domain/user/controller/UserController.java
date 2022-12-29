package com.manneung.careerup.domain.user.controller;


import com.manneung.careerup.domain.base.BaseEntity;
import com.manneung.careerup.domain.base.BaseResponse;
import com.manneung.careerup.domain.base.BaseResponseStatus;
import com.manneung.careerup.domain.user.model.dto.*;
import com.manneung.careerup.domain.user.service.UserService;
import com.manneung.careerup.global.jwt.JwtFilter;
import com.manneung.careerup.global.jwt.TokenProvider;
import com.manneung.careerup.global.jwt.TokenRes;
import com.manneung.careerup.global.s3.S3UploaderService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.io.IOException;

import static com.manneung.careerup.domain.base.BaseResponseStatus.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final S3UploaderService s3UploaderService;



    @ApiOperation(value = "회원 가입", notes = "authorities는 적지 않아도 됨")
    @PostMapping("/signup")
    //현재 요청형식으로 반환 중
    public ResponseEntity<BaseResponse<SignUpUserReq>> signup(@Valid @RequestBody SignUpUserReq signupUserReq) {
        return ResponseEntity.ok(BaseResponse.create(SUCCESS, userService.signup(signupUserReq)));
    }


    @ApiOperation(value = "회원 가입(ADMIN)", notes = "관리자 권한 계정 생성")
    @PostMapping("/signup-admin")
    public ResponseEntity<BaseResponse<SignUpUserReq>> signupAdmin(@Valid @RequestBody SignUpUserReq signupUserReq){
        return ResponseEntity.ok(BaseResponse.create(SUCCESS, userService.signupAdmin(signupUserReq)));
    }


    @ApiOperation(value = "회원 로그인", notes = "회원 로그인")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TokenRes>> login(@Valid @RequestBody LoginUserReq loginUserReq){
        TokenRes tokenRes = userService.login(loginUserReq);
        return ResponseEntity.ok(BaseResponse.create(SUCCESS,tokenRes));
    }


    @ApiOperation(value = "현재 로그인한 유저 정보 불러오기", notes = "내 정보 전부 불러오기")
    @GetMapping("")
    public ResponseEntity<BaseResponse<GetUserDetailRes>> getUserInfo() {
        GetUserDetailRes getUserDetailRes = userService.getUserInfo();
        return ResponseEntity.ok(BaseResponse.create(BaseResponseStatus.SUCCESS, getUserDetailRes));
    }


    //유저 정보 수정, 비밀번호, 프로필 사진 등등 수정 api
    @ApiOperation(value = "유저 정보 수정", notes = "유저 정보 수정")
    @PatchMapping("/modify")
    public ResponseEntity<BaseResponse<GetUserDetailRes>> modifyUserInfo(@RequestBody PatchUserReq patchUserReq) {
        GetUserDetailRes getUserDetailRes = userService.modifyUserInfo(patchUserReq);
        return ResponseEntity.ok(BaseResponse.create(BaseResponseStatus.SUCCESS, getUserDetailRes));
    }


    @ApiOperation(value = "프로필 이미지 설정", notes = "프로필 이미지")
    @PatchMapping("/picture")
    public ResponseEntity<BaseResponse<String>> setPicture(@RequestPart("data") MultipartFile multipartFile) throws IOException {

        if(multipartFile != null){
            String imageUrl = userService.setUserPicture(multipartFile);
            return ResponseEntity.ok(BaseResponse.create(SUCCESS, imageUrl));
        } else {
            return ResponseEntity.ok(BaseResponse.create(USER_FAILED_TO_SET_PICTURE));
        }
    }

    @PostMapping("/image-upload")
    @ResponseBody
    public String imageUpload(@RequestPart("data") MultipartFile multipartFile) throws IOException {
        return s3UploaderService.upload(multipartFile, "careerup-bucket", "image");
    }



    @ApiOperation(value = "비밀번호 찾기(임시 비밀번호 발급하기)", notes = "비밀번호 찾기")
    @GetMapping("/password")
    public ResponseEntity<BaseResponse<PasswordRes>> getTemporaryPassword(@RequestParam String email) {
        PasswordRes passwordRes = userService.getTemporaryPassword(email);

        if(passwordRes == null){
            return ResponseEntity.ok(BaseResponse.create(USER_NOT_EXIST_EMAIL_ERROR));
        } else {
            return ResponseEntity.ok(BaseResponse.create(BaseResponseStatus.SUCCESS, passwordRes));
        }
    }


    @ApiOperation(value = "비밀번호 재설정(임시 비밀번호로 로그인 후 새로운 비밀번호 설정)", notes = "비밀번호 재설정")
    @PatchMapping("/password")
    public ResponseEntity<BaseResponse<PasswordRes>> setNewPassword(@RequestParam String newPassword) {
        PasswordRes passwordRes = userService.setPassword(newPassword);

        if(passwordRes == null){
            return ResponseEntity.ok(BaseResponse.create(USER_FAILED_TO_SET_PASSWORD));
        } else {
            return ResponseEntity.ok(BaseResponse.create(BaseResponseStatus.SUCCESS, passwordRes));
        }
    }





    //권한별 접근 가능 체크 api
    @ApiOperation(value = "USER, ADMIN 권한 접근 가능 api", notes = "USER, ADMIN 권한 접근 가능 api")
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<SignUpUserReq> getMyUserWithAuthorities() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
    }

    @ApiOperation(value = "ADMIN 권한 접근 가능 api", notes = "ADMIN 권한 접근 가능 api")
    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiImplicitParam(name = "username", value = "이메일 입력하기")
    public ResponseEntity<SignUpUserReq> getUserWithAuthorities(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
    }

}
