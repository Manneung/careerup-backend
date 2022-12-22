package com.manneung.careerup.domain.user.service;


import com.manneung.careerup.domain.user.model.Authority;
import com.manneung.careerup.domain.user.model.User;
import com.manneung.careerup.domain.user.model.dto.GetUserDetailRes;
import com.manneung.careerup.domain.user.model.dto.PatchUserReq;
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


    public User findNowLoginUser(){
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername).orElse(null);
    }


    @Transactional
    //지금 요청형식으로 반환중...!
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
                .name(signupUserReq.getName())
                .birth(signupUserReq.getBirth())
                .phone(signupUserReq.getPhone())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return SignUpUserReq.from(userRepository.save(user));
    }


    @Transactional
    public SignUpUserReq signupAdmin(SignUpUserReq signupUserReq) {
        if (userRepository.findOneWithAuthoritiesByUsername(signupUserReq.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                //.authorityName("ROLE_USER")
                .authorityName("ROLE_ADMIN")
                .build();

        User user = User.builder()
                .username(signupUserReq.getUsername())
                .password(passwordEncoder.encode(signupUserReq.getPassword()))
                .nickname(signupUserReq.getNickname())
                .name(signupUserReq.getName())
                .birth(signupUserReq.getBirth())
                .phone(signupUserReq.getPhone())
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

    @Transactional
    public GetUserDetailRes modifyUserInfo(PatchUserReq patchUserReq) {
        //현재 로그인한 유저 불러오기
        User user = findNowLoginUser();

        //유저 정보 수정
        if(patchUserReq.getNickname() != null)
            user.setNickname(patchUserReq.getNickname());
        if(patchUserReq.getPhone() != null)
            user.setPhone(patchUserReq.getPhone());
        if(patchUserReq.getPicture() != null)
            user.setPicture(patchUserReq.getPicture());
        if(patchUserReq.getJob() != null)
            user.setJob(patchUserReq.getJob());
        if(patchUserReq.getAddress() != null)
            user.setAddress(patchUserReq.getAddress());
        if(patchUserReq.getUniv() != null)
            user.setUniv(patchUserReq.getUniv());
        if(patchUserReq.getMajor1() != null)
            user.setMajor1(patchUserReq.getMajor1());
        if(patchUserReq.getMajor2() != null)
            user.setMajor1(patchUserReq.getMajor2());
        if(patchUserReq.getInterestField1() != null)
            user.setInterestField1(patchUserReq.getInterestField1());
        if(patchUserReq.getInterestField2() != null)
            user.setInterestField2(patchUserReq.getInterestField2());
        if(patchUserReq.getInterestField3() != null)
            user.setInterestField3(patchUserReq.getInterestField3());

        //DB에 바뀐 정보 저장
        userRepository.save(user);

        return GetUserDetailRes.toGetUserDetailRes(user);
    }

    public GetUserDetailRes getUserInfo() {
        //현재 로그인한 유저 불러오기
        User user = findNowLoginUser();

        if(user != null){
            return GetUserDetailRes.toGetUserDetailRes(user);
        } else {
            return null;
        }
    }
}
