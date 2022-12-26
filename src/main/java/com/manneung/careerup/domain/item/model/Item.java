package com.manneung.careerup.domain.item.model;


import com.manneung.careerup.domain.base.BaseEntity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Item extends BaseEntity {

    //아이템 기본 정보
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_idx")
    private int itemIdx;

    @Column(name = "map_idx")
    private int mapIdx;



    //활동 내용 정보들
    private int sequence; //순서 컬럼

    private String category;

    private String title; //활동 제목

    private String institution; //기관

    private String period;

    private String acquisition; //취득일

    private String field; //분야, 주제

    private String role; //맡은 역할

    private String content; //활동 내용

    //활동 사진은 file 엔티티로

    private String realization; //느낀 점

    //추가 첨부 파일(증명서, 합격증, 피피티 등)도 file 엔티티로



}