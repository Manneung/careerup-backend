package com.manneung.careerup.domain.user.model;


import com.manneung.careerup.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;


@RequiredArgsConstructor
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

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String picture;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String univ;

    @Column(nullable = false)
    private String major1;

    @Column
    private String major2;

    @Column(nullable = false, name = "interest_field1")
    private String interestField1;

    @Column(name = "interest_field2")
    private String interestField2;

    @Column(name = "interest_field3")
    private String interestField3;
}
