package com.manneung.careerup.global.jwt;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenRes {

    private int userIdx;
    private String username;
    private String accessToken;
    private String refreshToken;
}
