package com.manneung.careerup.domain.item.model.dto.item;

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
    //아이템 생성
    //mapIdx는 따로
    //각 아이템에 따라 필요한 항목만 채워서 실행하기

    private int sequence; //순서

    private String category;

    private String title;

    private String subtitle; //활동 부제목

    private String institution; //기관

    private String period;

    private String acquisition; //취득일

    private String field; //분야, 주제

    private String role; //맡은 역할

    private String content; //활동 내용

    private String realization; //느낀 점



}
