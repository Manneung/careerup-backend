package com.manneung.careerup.test;


import com.manneung.careerup.domain.base.BaseResponse;
import com.manneung.careerup.domain.user.model.dto.GetUserRes;
import com.manneung.careerup.global.email.EmailAuthRequestDto;
import com.manneung.careerup.global.email.EmailAuthResponseDto;
import com.manneung.careerup.global.email.EmailService;
import com.manneung.careerup.global.jwt.JwtFilter;

import com.manneung.careerup.global.jwt.TokenProvider;
import com.manneung.careerup.global.s3.S3UploaderService;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.manneung.careerup.domain.base.BaseResponseStatus.FAIL;
import static com.manneung.careerup.domain.base.BaseResponseStatus.SUCCESS;


@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder; //AuthenticationManagerBuilder 이건 만든 거 아님
    private final S3UploaderService s3UploaderService;
    private final EmailService emailService;

    @PostMapping("login/mailConfirm")
    public ResponseEntity<BaseResponse<EmailAuthResponseDto>> mailConfirm(@RequestBody EmailAuthRequestDto emailDto) throws MessagingException, UnsupportedEncodingException {

        EmailAuthResponseDto emailAuthResponseDto = emailService.sendEmail(emailDto.getEmail());
        return ResponseEntity.ok(BaseResponse.create(SUCCESS, emailAuthResponseDto));
    }

    @ApiOperation(value = "테스트 컨트롤러 성공 api", notes = "테스트 컨트롤러 성공 api")
    @GetMapping("/success")
    public ResponseEntity<BaseResponse<TestRes>> testSuccess(){
        TestRes testRes = new TestRes("test success api");
        return ResponseEntity.ok(BaseResponse.create(SUCCESS, testRes));
    }


    @PostMapping("/image-upload")
    @ResponseBody
    public String imageUpload(@RequestPart("data") MultipartFile multipartFile) throws IOException {
        return s3UploaderService.upload(multipartFile, "careerup-bucket", "image");
    }




    @ApiOperation(value = "테스트 컨트롤러 에러 api", notes = "테스트 컨트롤러 에러 api")
    @GetMapping("/error")
    public ResponseEntity<BaseResponse<TestRes>> testFail(){
        TestRes testRes = new TestRes("test error api");
        return ResponseEntity.ok(BaseResponse.create(FAIL, testRes));
    }

    @ApiOperation(value = "테스트 컨트롤러 post 테스트 api", notes = "테스트 컨트롤러 post 테스트 api")
    @PostMapping("/test-post")
    public ResponseEntity<BaseResponse<TestRes>> testPost(@RequestBody TestReq testReq){
        TestRes testRes = new TestRes(testReq.getMessage());
        return ResponseEntity.ok(BaseResponse.create(SUCCESS, testRes));
    }

//    @ApiOperation(value = "테스트 컨트롤러 로그인테스트", notes = "로그인 테스트(이메일, 비밀번호)")
//    @PostMapping("/test-login")
//    public ResponseEntity<BaseResponse<TokenInfoRes>> testLogin(@Valid @RequestBody TestLoginReq testLoginReq){
//
//        //이메일과 비밀번호로 authenticationToken 생성
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(testLoginReq.getEmail(), testLoginReq.getPassword());
//
//        //authenticationToken를 이용해서 authentication객체를 생성하려고 authenticate메소드가 실행될 때, loadUserByUsername메소드가 실행된다
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication); //authentication를 SecurityContext 저장
//
//        TokenInfoRes tokenInfoRes = tokenProvider.createToken(authentication); //인증 정보를 기준으로 jwt토큰 생성
//        String jwt = tokenInfoRes.getAccessToken();
//
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt); //응답 헤더에도 넣음
//
//        return ResponseEntity.ok(BaseResponse.create(SUCCESS, tokenInfoRes));
//
//        //return ResponseEntity.ok( BaseResponse.create(SUCCESS, (new TokenInfoRes(jwt), httpHeaders, HttpStatus.OK)));
//
//    }
}
