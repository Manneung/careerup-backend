package com.manneung.careerup.domain.item.model;


import com.manneung.careerup.domain.base.BaseEntity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;


@DynamicInsert
@NoArgsConstructor
//@AllArgsConstructor
@Setter
@Getter
@Entity
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_idx")
    private int itemIdx;


    @Column(name = "map_idx")
    private int mapIdx;

    private String title;

    //@Enumerated(EnumType.STRING)
    private String category; //활동 카테고리

    private int sequence; //순서 컬럼






    @Builder
    public Item(int mapIdx, String title, String category, int sequence){
        this.mapIdx = mapIdx;
        this.title = title;
        this.category = category;
        this.sequence = sequence;
        //this.status = status;
    }

    public Item toEntity() {
        return Item.builder()
                .mapIdx(mapIdx)
                .title(title)
                .category(category)
                .sequence(sequence)
                //.status("A")
                .build();
    }

}