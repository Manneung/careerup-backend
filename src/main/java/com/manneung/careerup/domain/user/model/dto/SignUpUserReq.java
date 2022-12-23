package com.manneung.careerup.domain.user.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.manneung.careerup.domain.user.model.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpUserReq {
    //이름, 이메일, 비밀번호

    @NotNull
    @Size(min = 3, max = 50)
    private String username;  //이메일

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    private String name;



    //입력할 필요 없음
    private Set<AuthorityRes> authorities;

    public static SignUpUserReq from(User user) {
        if(user == null) return null;

        return SignUpUserReq.builder()
                .username(user.getUsername())
                .name(user.getName())
                .authorities(user.getAuthorities().stream()
                        .map(authority -> AuthorityRes.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
