package com.manneung.careerup.domain.item.model;


import com.manneung.careerup.domain.item.constant.Category;
import com.manneung.careerup.domain.user.model.Role;
import com.manneung.careerup.domain.user.model.User;
import lombok.*;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_idx")
    private int itemIdx;


    @Column(name = "map_idx")
    private int mapIdx; //해당 활동을 추가했던 map인덱스

    @Enumerated(EnumType.STRING)
    private Category category; //활동 카테고리

    private String period;

    private String photo; //활동 사진

    private int count; //순서 컬럼


/*
    @Builder
    public Item(int mapIdx, Category category, ){
        this.mapIdx = mapIdx;
        this.
    }

    public Item toEntity() {
        return Item.builder()
                .mapIdx(mapIdx)
                .

                .build();
    }*/

}