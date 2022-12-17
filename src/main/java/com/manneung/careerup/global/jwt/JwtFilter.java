package com.manneung.careerup.global.jwt;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    //jwt를 위한 커스텀필터를 만들기 위한 클래스

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;


    //실제 필터링 로직
    //토큰의 인증정보를 SecurityContext에 저장하는 역할 수행
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(request);
        String requestURI = request.getRequestURI();

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication); //토큰이 유효하다면 SecurityContextHolder에 set
            logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {
            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }


        filterChain.doFilter(request, response);
    }




//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String jwt = resolveToken(request);
//        String requestURI = request.getRequestURI();
//        try{
//            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
//                Authentication authentication = tokenProvider.getAuthentication(jwt);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                log.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
//            }}
//        catch (SecurityException | MalformedJwtException e) {
//            request.setAttribute("exception", JWT_WRONG_TYPE_TOKEN.getCode());
//        } catch (ExpiredJwtException e) {
//            request.setAttribute("exception", JWT_EXPIRED_TOKEN.getCode());
//        } catch (UnsupportedJwtException e) {
//            request.setAttribute("exception", JWT_UNSUPPORTED_TOKEN.getCode());
//        } catch (IllegalArgumentException e) {
//            request.setAttribute("exception", JWT_ACCESS_DENIED.getCode());
//        } catch (Exception e) {
//            log.error("================================================");
//            log.error("JwtFilter - doFilterInternal() 오류발생");
//            log.error("token : {}", jwt);
//            log.error("Exception Message : {}", e.getMessage());
//            log.error("Exception StackTrace : {");
//            e.printStackTrace();
//            log.error("}");
//            log.error("================================================");
//            request.setAttribute("exception", JWT_UNKNOWN_ERROR.getCode());
//        }
//
//        filterChain.doFilter(request, response);
//    }

//    extends GenericFilterBean
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//        String jwt = resolveToken(httpServletRequest); //httpServletRequest에서 jwt토큰 추출
//        String requestURI = httpServletRequest.getRequestURI();
//
//        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
//            Authentication authentication = tokenProvider.getAuthentication(jwt);
//            SecurityContextHolder.getContext().setAuthentication(authentication); //토큰이 유효하다면 SecurityContextHolder에 set
//            logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
//        } else {
//            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
//        }
//
//        filterChain.doFilter(servletRequest, servletResponse);
//    }




    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}