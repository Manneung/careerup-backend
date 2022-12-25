package com.manneung.careerup.domain.item.model.dto;

import com.manneung.careerup.domain.item.model.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PostItemReq {
    private String title;
    private String category;
    private int sequence;


//    public Item toEntity(int mapIdx, PostItemReq postItemReq) {
//        return Item.builder()
//                .mapIdx(mapIdx)
//                .title(postItemReq.getTitle())
//                .category(postItemReq.getCategory())
//                .sequence(postItemReq.getSequence())
//                //.status("A")
//                .build();
//    }

}
