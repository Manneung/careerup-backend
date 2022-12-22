package com.manneung.careerup.domain.map.model;


import com.fasterxml.jackson.databind.ser.Serializers;
import com.manneung.careerup.domain.base.BaseEntity;
import com.manneung.careerup.domain.item.model.Item;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;


@DynamicInsert
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
    private String nickname;

    @Column()
    private String title;

    @Column(nullable = false)
    private String job;



}
