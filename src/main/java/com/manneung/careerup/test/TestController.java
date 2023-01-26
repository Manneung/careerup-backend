package com.manneung.careerup.test;


import com.manneung.careerup.domain.base.BaseResponse;
import com.manneung.careerup.global.email.EmailAuthRequestDto;
import com.manneung.careerup.global.email.EmailAuthResponseDto;
import com.manneung.careerup.global.email.EmailService;

import com.manneung.careerup.global.jwt.TokenProvider;
import com.manneung.careerup.global.s3.S3UploaderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.manneung.careerup.domain.base.BaseResponseStatus.FAIL;
import static com.manneung.careerup.domain.base.BaseResponseStatus.SUCCESS;


@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    private final S3UploaderService s3UploaderService;
    private final EmailService emailService;

    @ApiOperation(value = "테스트 이메일 인증 api", notes = "테스트 이메일 인증 api")
    @PostMapping("/login/mailConfirm")
    public ResponseEntity<BaseResponse<EmailAuthResponseDto>> mailConfirm(@RequestBody EmailAuthRequestDto emailDto) throws MessagingException, UnsupportedEncodingException {

        EmailAuthResponseDto emailAuthResponseDto = emailService.sendEmail(emailDto.getEmail());
        return ResponseEntity.ok(BaseResponse.ok(SUCCESS, emailAuthResponseDto));
    }

    @ApiOperation(value = "테스트 컨트롤러 성공 api", notes = "테스트 컨트롤러 성공 api")
    @GetMapping("/success")
    public ResponseEntity<BaseResponse<TestRes>> testSuccess(){
        TestRes testRes = new TestRes("test success api");
        return ResponseEntity.ok(BaseResponse.ok(SUCCESS, testRes));
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
        return ResponseEntity.ok(BaseResponse.ok(FAIL, testRes));
    }

    @ApiOperation(value = "테스트 컨트롤러 post 테스트 api", notes = "테스트 컨트롤러 post 테스트 api")
    @PostMapping("/test-post")
    public ResponseEntity<BaseResponse<TestRes>> testPost(@RequestBody TestReq testReq){
        TestRes testRes = new TestRes(testReq.getMessage());
        return ResponseEntity.ok(BaseResponse.ok(SUCCESS, testRes));
    }

}
