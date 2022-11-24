package com.manneung.careerup;


import com.manneung.careerup.domain.base.BaseResponse;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public BaseResponse<String> test(){
        String test = "test Success";
        return new BaseResponse<>(test);
    }

}
