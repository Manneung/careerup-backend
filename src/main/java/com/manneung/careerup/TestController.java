package com.manneung.careerup;


import com.manneung.careerup.domain.base.BaseResponse;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.manneung.careerup.domain.base.BaseResponseStatus.FAIL;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/success")
    public BaseResponse<String> testSuccess(){
        String test = "test Success";
        return new BaseResponse<>(test);
    }

    @GetMapping("/error")
    public BaseResponse<String> testFail(){
        return new BaseResponse<>(FAIL);
    }
}
