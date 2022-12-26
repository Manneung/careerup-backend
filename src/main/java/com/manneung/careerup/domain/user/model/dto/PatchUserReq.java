package com.manneung.careerup.domain.user.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PatchUserReq {
    //유저 컬럼 중 수정할 수 있는 컬럼만
    //비밀번호는 따로 처리


    private String age;

    private String nickname;

    private String phone;

    private String picture;

    private String job;

    private String address;



    private String link;

    private String univ;

    private String major1;

    private String major2;

    private String interestField1;

    private String interestField2;

    private String interestField3;
}
