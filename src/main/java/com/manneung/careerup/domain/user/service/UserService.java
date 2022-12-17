package com.manneung.careerup.domain.user.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.manneung.careerup.domain.user.model.*;
import com.manneung.careerup.domain.user.model.dto.GoogleLoginReq;
import com.manneung.careerup.domain.user.model.dto.LoginUserReq;
import com.manneung.careerup.domain.user.model.dto.LoginUserRes;
import com.manneung.careerup.domain.user.model.dto.NaverLoginReq;
import com.manneung.careerup.domain.user.repository.UserRepository;

import com.manneung.careerup.global.jwt.EToken;
import com.manneung.careerup.global.jwt.TokenInfoRes;
import com.manneung.careerup.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.manneung.careerup.domain.user.model.Role.ROLE_USER;

@PropertySource(value = "application.yml")
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    public String googleClientId;


    //DB에 유저의 기본적인 이름과 프로필 이미지, 이메일 저장
    @Transactional
    public String saveOrUpdate(User user) { //user DB에 저장 또는 업데이트
        User myUser = userRepository.findByEmail(user.getEmail())
                .map(entity -> entity.update(user.getName(), user.getPicture()))
                .orElse(user.toEntity());

        userRepository.save(myUser);

        String userEmail = myUser.getEmail();


        return userEmail;
    }

    //DB에 유저의 추가정보(전공, 관심분야 등 저장)


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //테스트 로그인
    //이부분은 수정이 필요한 부분
//    public LoginUserRes signup(LoginUserReq loginUserReq) { //회원가입 로직
//        if (userRepository.findOneWithAuthoritiesByUsername(loginUserReq.getUsername()).orElse(null) != null) {
//            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
//        }
//
//        Authority authority = Authority.builder()
//                .authorityName("ROLE_USER")
//                .build();
//
//
//        //유저정보와 권한정보 저장
//        User user = User.builder()
//                .username(userDto.getUsername())
//                .password(passwordEncoder.encode(userDto.getPassword()))
//                .nickname(userDto.getNickname())
//                .authorities(Collections.singleton(authority))
//                .activated(true)
//                .build();
//
//        return UserDto.from(userRepository.save(user));
//    }


    public LoginUserRes loginUser(LoginUserReq loginUserReq) {
        //System.out.println("로그인테스트1");
        //User user = this.validateEmail(loginRequest.getEmail());
        System.out.println("로그인테스트2");
        TokenInfoRes tokenInfoRes = this.validateLogin(loginUserReq);
        return LoginUserRes.from(tokenInfoRes, loginUserReq.getEmail());
    }

    public TokenInfoRes validateLogin(LoginUserReq loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication = this.authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        TokenInfoRes tokenInfoRes = this.tokenProvider.createToken(authentication);
        this.redisTemplate.opsForValue()
                .set(EToken.eRefreshToken.getMessage() + authentication.getName(),
                        tokenInfoRes.getRefreshToken(), tokenInfoRes.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
        return tokenInfoRes;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //소셜로그인 비즈니스 로직(구글)
    public LoginUserRes authGoogleUser(GoogleLoginReq googleLoginReq){
        String tokenId = googleLoginReq.getToken(); //req에서 tokeId 할당
        log.info(tokenId);

        JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        HttpTransport transport = new NetHttpTransport();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                                                                  .setAudience(Collections.singletonList(googleClientId))
                                                                  .build();

        //String idTokenString = req.getParameter("googleIdtoken"); //프론트엔드로부터 넘겨받은 id token

        try {
            GoogleIdToken googleIdToken = verifier.verify(tokenId); //tokenId 유효성 검사
            System.out.println("googleIdToken: " + googleIdToken);


            if (googleIdToken != null) { //googleIdToken이 유효성 검사 통과
                GoogleIdToken.Payload payload = googleIdToken.getPayload(); //payload값 할당

                //payload에서 값 하나씩 꺼내기
                String email = payload.getEmail();
                String name = (String) payload.get("name");
                String picture = (String) payload.get("picture");
                Boolean emailVerified=payload.getEmailVerified();

                log.info(email);
                log.info(name);
                log.info(picture);


                //해시로 키, 밸류 정의
                Map userMap = new HashMap<String, String>();
                userMap.put("email", email);
                userMap.put("name", name);
                userMap.put("picture", picture);
                userMap.put("emailVerified", emailVerified);


                //저장할 정보 User 객체로 만들고, 저장 후 유저의 이메일로 리턴받음
                User toSaveUser = new User(emailVerified, name,email,picture, ROLE_USER);
                String userEmail = saveOrUpdate(toSaveUser);



                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                authorities.add(new SimpleGrantedAuthority(String.valueOf(ROLE_USER)));


                //userDetails에 현재 사용자가 가지고 있는 권한(ROLE_USER), 해당 유저 정보, 키로 활용할 이메일
                OAuth2User userDetails = new DefaultOAuth2User(authorities, userMap, "email");

                OAuth2AuthenticationToken auth = new OAuth2AuthenticationToken(userDetails, authorities, "email");
                auth.setDetails(userDetails);

                SecurityContextHolder.getContext().setAuthentication(auth);

                //인증 정보를 기반으로 응답 토급 발급
                TokenInfoRes tokenInfoRes = tokenProvider.createToken(auth);

                return LoginUserRes.from(tokenInfoRes, userEmail);

            } else {  //googleIdToken이 유효성 검사 통과 못함
                String result = "Invalid ID token.";
                googleLoginReq.setToken(result); //req의 토큰값을 다른 걸로 바꿔버림
                return null;
            }
        }
        catch(Exception e){
            googleLoginReq.setToken("invalid Id token");
            return null;
        }
    }




    //소셜로그인 비즈니스 로직(네이버)
    public LoginUserRes authNaverUser(NaverLoginReq naverLoginReq) throws IOException {
        String token = naverLoginReq.getToken();
        String header = "Bearer " + token; // Bearer 다음에 공백 추가


        String apiURL = "https://openapi.naver.com/v1/nid/me";


        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", header);
        String responseBody = get(apiURL,requestHeaders);

        ObjectMapper mapper=new ObjectMapper();
        System.out.println(responseBody);
        // String json="{\"resultcode\": \"00\", \"message\": \"success\", \"response\": {\"email\": \"openapi@naver.com\", \"name\": \"OpenAPI\", \"profile_image\": \"https://ssl.pstatic.net/static/pwe/address/nodata_33x33.gif\"}}";
        Map<String, String> map= mapper.readValue(responseBody,Map.class);

        Map map2= mapper.readValue(responseBody,Map.class);

        Map userMap= (Map) map2.get("response");


        //유저 이메일, 이름, 프로필이미지
        String email=userMap.get("email").toString();
        log.info(email);
        String name=userMap.get("name").toString();
        log.info(name);
        String pictureUrl=userMap.get("profile_image").toString();
        log.info(pictureUrl);

        Boolean emailVerified=true;

        User user = new User(emailVerified, name,email,pictureUrl, ROLE_USER);

        String userEmail = saveOrUpdate(user);
        /**
         * Spring Security by jungwoo
         */
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(String.valueOf(ROLE_USER)));
        OAuth2User userDetails = new DefaultOAuth2User(authorities, userMap, "email");
        OAuth2AuthenticationToken auth = new OAuth2AuthenticationToken(userDetails, authorities, "email");
        auth.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
        TokenInfoRes tokenInfoResponse = tokenProvider.createToken(auth);

        //토큰 정보와 저장한 이메일을 같이 리턴
        return LoginUserRes.from(tokenInfoResponse, userEmail);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //네이버 로그인에 필요한 추가 함수
    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

}
