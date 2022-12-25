package com.manneung.careerup.domain.map.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetMapRes {
    //마이페이지에서 맵 리스트 보여줄 때 사용할 dto

    private int mapIdx;
    private String title;

}
