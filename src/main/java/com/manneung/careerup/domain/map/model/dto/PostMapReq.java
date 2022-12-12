package com.manneung.careerup.domain.map.model.dto;

import com.manneung.careerup.domain.item.model.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.List;



@Setter
@Getter
public class PostMapReq {

    private Integer userIdx;
    private String title;
    private String name;
    private String job;
    private List<Item> itemList;

}
