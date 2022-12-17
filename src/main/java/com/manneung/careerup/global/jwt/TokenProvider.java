package com.manneung.careerup.global.jwt;


import com.manneung.careerup.domain.user.model.CustomUserDetails;
import com.manneung.careerup.domain.user.model.User;
import com.manneung.careerup.domain.user.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@Service
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-validity-in-seconds}")
    private long accessTokenValidityTime;

    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenValidityTime;

    private final UserRepository userRepository;

    private Key key;



    /*
     * 시크릿 키 설정
     */
    @Override
    public void afterPropertiesSet() {
        //빈이 생성되고 주입을 받은 후에 시크릿값을 Base64로 디코드해서 key변수에 할당하기 위함
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    /*
     * 검증된 이메일에 대해 토큰을 생성하는 메서드
     * AccessToken의 Claim으로는 email과 nickname을 넣습니다.
     */
    public TokenInfoRes createToken(Authentication authentication) {
        //Authentication객체의 권한 정보를 이용해서 토큰을 생성하는 메소드
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date accessTokenValidity = new Date(now + this.accessTokenValidityTime);
        Date refreshTokenValidity = new Date(now + this.refreshTokenValidityTime);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(accessTokenValidity)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenValidity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenInfoRes.from("Bearer", accessToken, refreshToken, refreshTokenValidityTime);

    }


    /*
     * 권한 가져오는 메서드
     */
    public Authentication getAuthentication(String token) {

        Claims claims = parseClaims(token); //토큰을 이용해서 claim생성
//        Claims claims = Jwts //토큰을 이용해서 claim생성
//                .parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();


        //클레임에서 권한 정보를 빼낸 후 이를 이용해서 유저 객체를 만들어서 최종적으로 Authentication객체를 리턴
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        //권한 정보들을 이용해서 유저 객체 생성
        //User user = new User(claims.getSubject(), "", authorities);
        User user = this.userRepository.findByEmail(claims.getSubject()).orElseThrow(); // ->이메일, 비밀번호 버전


        //유저 객체와 토큰, 권한 정보를 이용해서 Authentication객체를 리턴

        //orElseThrow(new BaseResponse<>(USER_NOT_EXIST_EMAIL_ERROR))
        return new UsernamePasswordAuthenticationToken(new CustomUserDetails(user), token, authorities);
    }


    /*
     * 토큰 유효성 검사하는 메서드
     */
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            //throw e;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            //throw e;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            //throw e;
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            //throw e;
        }

        return false;
    }


    /*
     * 리프레시 토큰 유효성 검사하는 메서드
     */
    public boolean validateRefreshToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            //throw e;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            //throw e;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            //throw e;
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            //throw e;
        } //finally {
//            return false;
//        }

        return false;
    }

    /*
     * 토큰에서 Claim 추츨하는 메서드
     */
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Long getExpiration(String accessToken) {
        Date expiration = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getExpiration();
        Long now = new Date().getTime();
        return (expiration.getTime() - now);
    }
}