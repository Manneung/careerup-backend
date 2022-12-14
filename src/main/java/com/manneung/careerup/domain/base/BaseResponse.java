package com.manneung.careerup.domain.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.manneung.careerup.test.TestRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.manneung.careerup.domain.base.BaseResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class BaseResponse<T> {
    //API base응답

    private String code;
    private  String message;
    private T result;


    public static <T> BaseResponse<T> create(BaseResponseStatus baseResponseStatus) {
        //return new BaseResponse<>(code, message, null);
        return new BaseResponse<>(baseResponseStatus.getCode(), baseResponseStatus.getMessage(), null);
    }
    public static <T> BaseResponse<T> create(BaseResponseStatus baseResponseStatus, T result) {
        return new BaseResponse<>(baseResponseStatus.getCode(), baseResponseStatus.getMessage(), result);
    }

}

