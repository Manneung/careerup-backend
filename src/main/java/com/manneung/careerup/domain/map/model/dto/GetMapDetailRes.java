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

    private int mapIdx;
    private String nickname;
    private List<GetItemRes> itemList;

}
