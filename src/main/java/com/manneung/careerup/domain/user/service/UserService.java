package com.manneung.careerup.domain.user.service;


import com.manneung.careerup.domain.user.model.Authority;
import com.manneung.careerup.domain.user.model.User;
import com.manneung.careerup.domain.user.model.dto.*;
import com.manneung.careerup.domain.user.repository.UserRepository;
import com.manneung.careerup.global.jwt.JwtFilter;
import com.manneung.careerup.global.jwt.SecurityUtil;
import com.manneung.careerup.global.jwt.TokenProvider;
import com.manneung.careerup.global.jwt.TokenRes;
import com.manneung.careerup.global.s3.S3UploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Random;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final S3UploaderService s3UploaderService;



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
                //.nickname(signupUserReq.getNickname())
                .name(signupUserReq.getName())
                //.birth(signupUserReq.getBirth())
                //.phone(signupUserReq.getPhone())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return SignUpUserReq.from(userRepository.save(user));
    }


    @Transactional
    public TokenRes login(LoginUserReq loginUserReq){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserReq.getUsername(), loginUserReq.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new TokenRes(jwt);
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
                //.nickname(signupUserReq.getNickname())
                .name(signupUserReq.getName())
                //.birth(signupUserReq.getBirth())
                //.phone(signupUserReq.getPhone())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return SignUpUserReq.from(userRepository.save(user));
    }




//
//    @Transactional(readOnly = true)
//    public SignUpUserReq getUserWithAuthorities(String username) {
//        return SignUpUserReq.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
//    }
//
//    @Transactional(readOnly = true)
//    public SignUpUserReq getMyUserWithAuthorities() {
//        return SignUpUserReq.from(SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername).orElse(null));
//    }

    @Transactional
    public GetUserDetailRes modifyUserInfo(PatchUserReq patchUserReq) {
        //현재 로그인한 유저 불러오기
        User user = findNowLoginUser();

        //유저 정보 수정
        if(patchUserReq.getAge() != null)
            user.setAge(patchUserReq.getAge());
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
        if(patchUserReq.getGender() != null)
            user.setGender(patchUserReq.getGender());



        if(patchUserReq.getLink() != null)
            user.setLink(patchUserReq.getLink());
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

        return GetUserDetailRes.from(user);
    }


    //유저 프로필 사진 설정
    @Transactional
    public String setUserPicture(MultipartFile multipartFile) throws IOException {
        User user = findNowLoginUser();

        String imageUrl = s3UploaderService.upload(multipartFile, "careerup-bucket", "image");

        user.setPicture(imageUrl);
        userRepository.save(user);

        return imageUrl;
    }


    public GetUserDetailRes getUserInfo() {
        //현재 로그인한 유저 불러오기
        User user = findNowLoginUser();

        if(user != null){
            return GetUserDetailRes.from(user);
        } else {
            return null;
        }
    }


    //스프링시큐리티에선 비밀번호 암호화만 존재
    //복호화가 있을 경우 보안상의 문제를 발생하기 때문
    //그래서 임시 비밀번호를 발급 후
    //로그인 해서 비밀번호 수정을 하는 방향을 개발..?
    /**
     * 비밀번호 찾기 -> 비밀번호 재설정
     * 1. 이메일로 임시 비밀번호 발급
     * 2. 이메일과 임시 비밀번호로 로그인
     * 3. 토큰 인가 버튼에 등록
     * 4. 새로운 비밀번호 등록
     * 5. (선택) 다시 로그인되는지 확인까지 가능
     * */


    //문자열 난수 발생(알파벳 + 숫자)
    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }



    public PasswordRes getTemporaryPassword(String email) {
        String temporaryPassword = randomString();

        User user = null;

        if(userRepository.existsUserByUsername(email)){
            user = userRepository.findUserByUsername(email);

            user.setPassword(passwordEncoder.encode(temporaryPassword));

            userRepository.save(user);

            return new PasswordRes(temporaryPassword);
        }

        return null;
    }

    public PasswordRes setPassword(String newPassword) {
        User user = findNowLoginUser();

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return new PasswordRes(newPassword);
    }
}
