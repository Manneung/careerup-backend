package com.manneung.careerup.domain.map.model;


import com.manneung.careerup.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name = "map")
public class Map extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "map_idx")
    private int mapIdx;

    @Column(nullable = false)
    private String job;

}
