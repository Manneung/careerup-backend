package com.manneung.careerup.domain.item.model;


import com.manneung.careerup.domain.base.BaseEntity;
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
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_idx")
    private int itemIdx;


    @Column(name = "map_idx")
    private int mapIdx; //해당 활동을 추가했던 map인덱스

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