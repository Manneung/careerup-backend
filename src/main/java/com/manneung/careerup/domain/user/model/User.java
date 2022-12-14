package com.manneung.careerup.domain.user.model;


import com.manneung.careerup.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;



@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private int userIdx;


    //기본 로그인 정보
    @Column(nullable = false)
    private String email;

    //@Column(nullable = false)
    private String picture;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    @Column(name = "email_verified")
    private Boolean emailVerified;


    //추가 정보
//    @Column
//    private String nickname;
//
//    @Column
//    private String univ;
//
//    @Column
//    private String major1;
//
//    @Column
//    private String major2;
//
//    @Column(nullable = false, name = "interest_field1")
//    private String interestField1;
//
//    @Column(name = "interest_field2")
//    private String interestField2;
//
//    @Column(name = "interest_field3")
//    private String interestField3;
//
//    private String status;



    @Builder
    public User(boolean emailVerified, String name, String email, String picture, Role role) {
        this.emailVerified = emailVerified;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User toEntity() {
        return User.builder()
                .emailVerified(emailVerified)
                .name(name)
                .email(email)
                .picture(picture)
                .build();
    }


    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

}
