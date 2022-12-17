package com.manneung.careerup.global.jwt;

import com.manneung.careerup.domain.user.model.User;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static com.manneung.careerup.domain.user.model.Role.ROLE_USER;

@Getter
@Builder
public class OAuth2Attribute {
    private Map<String, Object> attributes;
    private String attributeKey;
    private String email;
    private String name;
    private String picture;

    public static OAuth2Attribute  of(String provider, String attributeKey, Map<String, Object> attributes) {
        switch (provider) {
            case "google": return ofGoogle(attributeKey, attributes);
            case "naver": return ofNaver("id", attributes);
            case "kakao": return ofKakao("email", attributes);
            default: throw new RuntimeException();
                // return ResponseEntity.ok(USER_NOT_EXIST_PROVIDER_ERROR);
        }
    }

    private static OAuth2Attribute ofGoogle(String attributeKey,
                                            Map<String, Object> attributes) {
        return OAuth2Attribute.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String)attributes.get("picture"))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();
    }

    private static OAuth2Attribute ofKakao(String attributeKey,
                                           Map<String, Object> attributes) {

//        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
//        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2Attribute.builder()
                .name((String) attributes.get("nickname"))
                .email((String) attributes.get("email"))
                .picture((String)attributes.get("profile_image_url"))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();

//        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
//        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
//
//        return OAuth2Attribute.builder()
//                .name((String) kakaoProfile.get("nickname"))
//                .email((String) kakaoAccount.get("email"))
//                .picture((String)kakaoProfile.get("profile_image_url"))
//                .attributes(kakaoAccount)
//                .attributeKey(attributeKey)
//                .build();


    }

    private static OAuth2Attribute ofNaver(String attributeKey,
                                           Map<String, Object> attributes) {
        //Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attribute.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("profile_image"))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();

//        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
//
//        return OAuth2Attribute.builder()
//                .name((String) response.get("name"))
//                .email((String) response.get("email"))
//                .picture((String) response.get("profile_image"))
//                .attributes(response)
//                .attributeKey(attributeKey)
//                .build();
    }


    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(ROLE_USER)
                .build();
    }

    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", attributeKey);
        map.put("key", attributeKey);
        map.put("name", name);
        map.put("email", email);
        map.put("picture", picture);

        return map;
    }
}