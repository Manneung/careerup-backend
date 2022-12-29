package com.manneung.careerup.domain.item.model.dto.etc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PostEtcReq {
    //아이템 생성
    //mapIdx는 따로

    private String category;

    private String title;

    private String period;

    private String role; //맡은 역할

    private String content; //활동 내용

    private String realization; //느낀 점



}
