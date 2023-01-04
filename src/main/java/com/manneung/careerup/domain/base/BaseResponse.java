package com.manneung.careerup.domain.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
@JsonPropertyOrder({"code", "message", "result"})
public class BaseResponse<T> {
    //API base응답

    private String code;
    private  String message;
    private T result;


    public static <T> BaseResponse<T> create(BaseResponseStatus baseResponseStatus) {
        return new BaseResponse<>(baseResponseStatus.getCode(), baseResponseStatus.getMessage(), null);
    }
    public static <T> BaseResponse<T> create(BaseResponseStatus baseResponseStatus, T result) {
        return new BaseResponse<>(baseResponseStatus.getCode(), baseResponseStatus.getMessage(), result);
    }


}

