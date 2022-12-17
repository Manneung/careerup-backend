package com.manneung.careerup.global.jwt;

import com.manneung.careerup.domain.base.BaseResponseStatus;
import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.manneung.careerup.domain.base.BaseResponseStatus.*;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String exception = (String)request.getAttribute("exception");


        if(exception == null) setResponse(response, JWT_UNKNOWN_ERROR);
            //잘못된 타입의 토큰인 경우
        else if(exception.equals(JWT_WRONG_TYPE_TOKEN.getCode()))
            setResponse(response, JWT_WRONG_TYPE_TOKEN);
            //토큰 만료된 경우
        else if(exception.equals(JWT_EXPIRED_TOKEN.getCode())) {
            System.out.println("토큰만료된경우");
            setResponse(response, JWT_EXPIRED_TOKEN);
        }
        //지원되지 않는 토큰인 경우
        else if(exception.equals(JWT_UNSUPPORTED_TOKEN.getCode()))
            setResponse(response, JWT_UNSUPPORTED_TOKEN);
        else
            setResponse(response, JWT_ACCESS_DENIED);

    }

    private void setResponse(HttpServletResponse response, BaseResponseStatus status) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  //401

        JSONObject responseJson = new JSONObject();
        responseJson.put("message", status.getMessage());
        responseJson.put("errorCode", status.getCode());

        response.getWriter().print(responseJson);
    }
}