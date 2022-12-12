package com.manneung.careerup.global.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EToken{
    eRefreshToken("RT:");
    private final String message;
}