package com.manneung.careerup.global.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity  //SpringSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.csrf().disable(); //CSRF 비활성화(로그인 화면 뜨는 거 해제)

        //h2 콘솔 연결 허가
        http
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .and()

                .headers()
                .frameOptions()
                .disable()
                .and()

                .csrf()
                .ignoringAntMatchers("/h2-console/**")
                .disable();

    }
}
