package com.manneung.careerup.domain.user.service;


import com.manneung.careerup.domain.user.model.Authority;
import com.manneung.careerup.domain.user.model.User;
import com.manneung.careerup.domain.user.model.dto.SignUpUserReq;
import com.manneung.careerup.domain.user.repository.UserRepository;
import com.manneung.careerup.global.jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Transactional
    public SignUpUserReq signup(SignUpUserReq signupUserReq) {
        if (userRepository.findOneWithAuthoritiesByUsername(signupUserReq.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(signupUserReq.getUsername())
                .password(passwordEncoder.encode(signupUserReq.getPassword()))
                .nickname(signupUserReq.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return SignUpUserReq.from(userRepository.save(user));
    }





    @Transactional(readOnly = true)
    public SignUpUserReq getUserWithAuthorities(String username) {
        return SignUpUserReq.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public SignUpUserReq getMyUserWithAuthorities() {
        return SignUpUserReq.from(SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername).orElse(null));
    }




}
