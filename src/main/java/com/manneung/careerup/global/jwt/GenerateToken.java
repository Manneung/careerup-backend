package com.manneung.careerup.global.jwt;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenerateToken {
    private String accessToken;
    private String refreshToken;
}
