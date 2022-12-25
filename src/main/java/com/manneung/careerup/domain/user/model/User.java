package com.manneung.careerup.domain.user.model;


import com.manneung.careerup.domain.base.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

@DynamicInsert
@DynamicUpdate
@Builder
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


    /////기본 로그인 정보/////
    @Column(nullable = false)
    private String username; //이메일입력

    private String password;

    private String name;

    @Column(name = "activated")
    private boolean activated;



    /////user 추가 정보/////
    private String age;

    @Column(nullable = false)
    private String nickname;

    private String phone;

    private String picture;

    private String job;

    private String address;

    private String univ;

    private String major1;

    private String major2;

    @Column(name = "interest_field1")
    private String interestField1;

    @Column(name = "interest_field2")
    private String interestField2;

    @Column(name = "interest_field3")
    private String interestField3;



    //권한 정보
    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_idx", referencedColumnName = "user_idx")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

}
