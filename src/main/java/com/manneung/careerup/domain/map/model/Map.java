package com.manneung.careerup.domain.map.model;


import com.manneung.careerup.domain.base.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


@DynamicInsert
@DynamicUpdate
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

    @Column(name = "user_idx")
    private int userIdx;

    @Column()
    private String title;

    @Column(nullable = false)
    private String career; //희망 직무



}
