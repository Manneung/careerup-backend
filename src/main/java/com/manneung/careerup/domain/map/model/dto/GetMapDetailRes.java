package com.manneung.careerup.domain.map.model.dto;


import com.manneung.careerup.domain.item.model.dto.GetItemRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GetMapDetailRes {
    //커리어맵 안에서 전부 보여줄 때

    private int mapIdx;

    //내 정보
    private String name;

    private String age;

    private String job;

    //맵 정보
    private String career;

    //아이템 정보
    private List<GetItemRes> itemList;

}
