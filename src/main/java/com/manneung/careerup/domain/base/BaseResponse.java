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
@JsonPropertyOrder({"code", "message", "result"})
public class BaseResponse<T> {

    private String code;
    private  String message;
    private T result;

    public static <T> BaseResponse<T> ok(BaseResponseStatus baseResponseStatus) {
        return new BaseResponse<>(baseResponseStatus.getCode(), baseResponseStatus.getMessage(), null);
    }
    public static <T> BaseResponse<T> ok(BaseResponseStatus baseResponseStatus, T result) {
        return new BaseResponse<>(baseResponseStatus.getCode(), baseResponseStatus.getMessage(), result);
    }

}

