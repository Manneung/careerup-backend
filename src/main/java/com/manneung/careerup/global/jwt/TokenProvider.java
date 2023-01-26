package com.manneung.careerup.global.jwt;

import com.manneung.careerup.domain.user.repository.UserRepository;
import com.manneung.careerup.domain.user.service.CustomUserDetailsService;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";


    private final String secret;
    private final UserRepository userRepository;
    private final String refreshSecret;
    private final CustomUserDetailsService customUserDetailsService;
    private final long refreshTime;
    private final long accessTime;
    private Key key;


    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.refresh}") String refreshSecret,
            UserRepository userRepository,
            CustomUserDetailsService customUserDetailsService,
            @Value("${jwt.access-token-seconds}") long accessTime,
            @Value("${jwt.refresh-token-seconds}")long refreshTime) {

        this.secret = secret;
        this.userRepository = userRepository;
        this.refreshSecret=refreshSecret;
        this.customUserDetailsService=customUserDetailsService;
        this.accessTime = accessTime*1000;
        this.refreshTime = refreshTime*1000;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.accessTime);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }


    public String createRefreshToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.refreshTime);


        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }


    public GenerateToken createAllToken(Authentication authentication){

        org.springframework.security.core.userdetails.User userDetail = (User) authentication.getPrincipal();
        com.manneung.careerup.domain.user.model.User findUser = userRepository.findUserByUsername(userDetail.getUsername());


        String accessToken=createToken(authentication);
        String refreshToken=createRefreshToken(authentication);

        //redisService.saveToken(String.valueOf(findUser.getUserIdx()),refreshToken, (System.currentTimeMillis()+ refreshTime*1000));

        return new GenerateToken(accessToken,refreshToken);
    }


    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(ServletRequest servletRequest, String token) {
        try {

            Jws<Claims> claims;
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            Long userIdx = claims.getBody().get("userIdx",Long.class);

            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

}