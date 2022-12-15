package com.manneung.careerup.test;


import com.manneung.careerup.domain.base.BaseResponse;
import com.manneung.careerup.global.jwt.TokenInfoRes;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.manneung.careerup.domain.base.BaseResponseStatus.FAIL;
import static com.manneung.careerup.domain.base.BaseResponseStatus.SUCCESS;

@RestController
@RequestMapping("/test")
public class TestController {

    @ApiOperation(value = "테스트 컨트롤러 성공 api", notes = "테스트 컨트롤러 성공 api")
    @GetMapping("/success")
    public ResponseEntity<BaseResponse<TestRes>> testSuccess(){
        TestRes testRes = new TestRes("test success api");
        return ResponseEntity.ok(BaseResponse.create(SUCCESS, testRes));
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
}
