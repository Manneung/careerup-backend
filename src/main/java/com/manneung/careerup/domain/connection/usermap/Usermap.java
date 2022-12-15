package com.manneung.careerup.domain.connection.usermap;


import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name = "usermap")
public class Usermap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_idx")
    private int userIdx;

    @Column(name = "map_idx")
    private int mapIdx;


    public Usermap(int userIdx, int mapIdx) {
        this.userIdx = userIdx;
        this.mapIdx = mapIdx;
    }
}
