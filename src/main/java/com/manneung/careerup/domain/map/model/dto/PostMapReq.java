package com.manneung.careerup.domain.map.model.dto;

import com.manneung.careerup.domain.item.model.dto.PostItemReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostMapReq {
    //맵 작성

    //맵 정보
    private String title;
    private String career;


}
