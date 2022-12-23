package com.manneung.careerup.global.config;

import com.manneung.careerup.global.jwt.JwtAccessDeniedHandler;
import com.manneung.careerup.global.jwt.JwtAuthenticationEntryPoint;
import com.manneung.careerup.global.jwt.JwtSecurityConfig;
import com.manneung.careerup.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity  //SpringSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resource/**", "/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // enable h2-console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v3/api-docs").permitAll()
                .antMatchers("/image/**").permitAll()
                .antMatchers("/test/**").permitAll()


//                .antMatchers("/map/**").permitAll()
//                .antMatchers("/item/**").permitAll()
                .antMatchers("/user/signup").permitAll()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/user/signup-admin").permitAll()
                .antMatchers("/user/password").permitAll()
                .anyRequest().authenticated() //위의 api가 아닌 경로는 모두 jwt 토큰 인증을 해야 함


                .and()
                .apply(new JwtSecurityConfig(tokenProvider))

        ;
    }
}
