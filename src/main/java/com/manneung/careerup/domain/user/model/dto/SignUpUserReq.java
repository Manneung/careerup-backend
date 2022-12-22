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

    @NotNull
    @Size(min = 3, max = 50)
    private String username;  //이메일

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    private String name;

    private String birth;

    @NotNull
    @Size(min = 3, max = 50)
    private String nickname;


    private String phone;


    //입력할 필요 없음
    private Set<AuthorityDto> authorityDtoSet;

    public static SignUpUserReq from(User user) {
        if(user == null) return null;

        return SignUpUserReq.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .name(user.getName())
                .birth(user.getBirth())
                .phone(user.getPhone())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
