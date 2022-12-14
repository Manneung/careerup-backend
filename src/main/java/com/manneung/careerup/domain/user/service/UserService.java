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


    public boolean existsUserByUsername(String username){
        return userRepository.existsUserByUsername(username);
    }


    public boolean loginPasswordCheck(String username, String password){
        User user = userRepository.findUserByUsername(username);

        if(passwordEncoder.matches(password, user.getPassword())){
            return true;
        } else{
            return false;
        }

    }


    @Transactional
    //?????? ?????????????????? ?????????...!
    public SignUpUserReq signup(SignUpUserReq signupUserReq) {
        if (userRepository.findOneWithAuthoritiesByUsername(signupUserReq.getUsername()).orElse(null) != null) {
            throw new RuntimeException("?????? ???????????? ?????? ???????????????.");
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




//    @Transactional
//    public SignUpUserReq signupAdmin(SignUpUserReq signupUserReq) {
//        if (userRepository.findOneWithAuthoritiesByUsername(signupUserReq.getUsername()).orElse(null) != null) {
//            throw new RuntimeException("?????? ???????????? ?????? ???????????????.");
//        }
//
//        Authority authority = Authority.builder()
//                //.authorityName("ROLE_USER")
//                .authorityName("ROLE_ADMIN")
//                .build();
//
//        User user = User.builder()
//                .username(signupUserReq.getUsername())
//                .password(passwordEncoder.encode(signupUserReq.getPassword()))
//                //.nickname(signupUserReq.getNickname())
//                .name(signupUserReq.getName())
//                //.birth(signupUserReq.getBirth())
//                //.phone(signupUserReq.getPhone())
//                .authorities(Collections.singleton(authority))
//                .activated(true)
//                .build();
//
//        return SignUpUserReq.from(userRepository.save(user));
//    }
//



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
        //?????? ???????????? ?????? ????????????
        User user = findNowLoginUser();

        //?????? ?????? ??????
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

        //DB??? ?????? ?????? ??????
        userRepository.save(user);

        return GetUserDetailRes.from(user);
    }


    //?????? ????????? ?????? ??????
    @Transactional
    public String setUserPicture(MultipartFile multipartFile) throws IOException {
        User user = findNowLoginUser();

        String imageUrl = s3UploaderService.upload(multipartFile, "careerup-bucket", "image");

        user.setPicture(imageUrl);
        userRepository.save(user);

        return imageUrl;
    }


    public GetUserDetailRes getUserInfo() {
        //?????? ???????????? ?????? ????????????
        User user = findNowLoginUser();

        if(user != null){
            return GetUserDetailRes.from(user);
        } else {
            return null;
        }
    }


    //??????????????????????????? ???????????? ???????????? ??????
    //???????????? ?????? ?????? ???????????? ????????? ???????????? ??????
    //????????? ?????? ??????????????? ?????? ???
    //????????? ?????? ???????????? ????????? ?????? ????????? ??????..?
    /**
     * ???????????? ?????? -> ???????????? ?????????
     * 1. ???????????? ?????? ???????????? ??????
     * 2. ???????????? ?????? ??????????????? ?????????
     * 3. ?????? ?????? ????????? ??????
     * 4. ????????? ???????????? ??????
     * 5. (??????) ?????? ?????????????????? ???????????? ??????
     * */


    //????????? ?????? ??????(????????? + ??????)
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
