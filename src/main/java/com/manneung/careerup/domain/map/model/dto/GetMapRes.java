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

    private int mapIdx;
    private String title;
    private String nickname;
}
