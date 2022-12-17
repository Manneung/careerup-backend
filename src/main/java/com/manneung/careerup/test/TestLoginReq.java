package com.manneung.careerup.test;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class TestLoginReq {

    private String email;
    private String password;
}
