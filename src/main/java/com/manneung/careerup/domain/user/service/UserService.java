package com.manneung.careerup.domain.user.service;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.manneung.careerup.domain.user.model.*;
import com.manneung.careerup.domain.user.repository.UserRepository;
import com.manneung.careerup.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.manneung.careerup.domain.user.Role.ROLE_USER;

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

    public User saveOrUpdate(User user) {
        User myuser = userRepository.findByEmail(user.getEmail())
                .map(entity -> entity.update(user.getName(), user.getPicture()))
                .orElse(user.toEntity());

        return userRepository.save(myuser);
    }

    public LoginUserRes authGoogleUser(GoogleLoginReq googleLoginReq){
        String tokenId = googleLoginReq.getToken(); // test받는 식으로 받던가 tokenId받는 식으로 받던가임
        log.info(tokenId);
        JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        HttpTransport transport = new NetHttpTransport();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(googleClientId))
                .build();

        //String idTokenString = req.getParameter("googleIdtoken"); //프론트엔드로부터 넘겨받은 id token

        try {
            GoogleIdToken idToken = verifier.verify(tokenId); //  verifies the JWT signature, the aud claim, the iss claim, and the exp claim.

            System.out.println("idToken"+idToken);

            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Print user identifier
                String email = payload.getEmail();
                String name = (String) payload.get("name");
                String picture = (String) payload.get("picture");
                Boolean emailVerified=payload.getEmailVerified();

                Map userMap = new HashMap<String, String>();
                userMap.put("email", email);
                userMap.put("name", name);
                userMap.put("pictureUrl", picture);
                userMap.put("emailVerified", emailVerified);


                log.info(email);
                log.info(name);
                log.info(picture);


                User user=new User(emailVerified, name,email,picture, ROLE_USER);
                System.out.println("setting");

                saveOrUpdate(user);
                /**
                 * Spring Security by jungwoo
                 */
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                authorities.add(new SimpleGrantedAuthority(String.valueOf(ROLE_USER)));
                OAuth2User userDetails = new DefaultOAuth2User(authorities, userMap, "email");
                OAuth2AuthenticationToken auth = new OAuth2AuthenticationToken(userDetails, authorities, "email");
                auth.setDetails(userDetails);
                SecurityContextHolder.getContext().setAuthentication(auth);
                TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(auth);

                return LoginUserRes.from(tokenInfoResponse);

            } else {
                String result = "Invalid ID token.";
                googleLoginReq.setToken(result);
                return null;
            }
        }
        catch(Exception e){
            googleLoginReq.setToken("invalid Id token");
            return null;
        }
    }


}
