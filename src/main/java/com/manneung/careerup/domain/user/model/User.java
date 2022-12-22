package com.manneung.careerup.domain.user.model;


import com.manneung.careerup.domain.base.BaseEntity;
import com.manneung.careerup.domain.map.model.Map;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@DynamicInsert
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


    //기본 로그인 정보
    @Column(nullable = false)
    private String username; //이메일입력

    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "activated")
    private boolean activated;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_idx", referencedColumnName = "user_idx")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;


    @ManyToMany
    @JoinTable(
            name = "user_map",
            joinColumns = {@JoinColumn(name = "user_idx", referencedColumnName = "user_idx")},
            inverseJoinColumns = {@JoinColumn(name = "map_idx", referencedColumnName = "map_idx")})
    private Set<Map> maps;


}
