package com.manneung.careerup.domain.user.model.dto;


import com.manneung.careerup.domain.user.model.User;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GetUserDetailRes {
    //유저 전체 정보 보여주는 DTO
    //마이페이지 정보들 전체


    //기본 유저 정보
    private String username; //이메일

    private String name;


    //추가 유저 정보
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


    public static GetUserDetailRes from(User user){
        return GetUserDetailRes.builder()
                .username(user.getUsername()) //이메일
                .name(user.getName())
                .age(user.getAge())
                .nickname(user.getNickname())
                .phone(user.getPhone())
                .picture(user.getPicture())
                .job(user.getJob())
                .address(user.getAddress())
                .link(user.getLink())
                .univ(user.getUniv())
                .major1(user.getMajor1())
                .major2(user.getMajor2())
                .interestField1(user.getInterestField1())
                .interestField2(user.getInterestField2())
                .interestField3(user.getInterestField3())
                .build();
    }

}
