package com.manneung.careerup.domain.item.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GetItemDetailRes {
    private int mapIdx;
    private String title;
    private String category;

}
