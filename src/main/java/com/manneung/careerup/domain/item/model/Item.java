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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_idx")
    private int itemIdx;

    //아이템 기본 정보
    @Column(name = "map_idx")
    private int mapIdx;
    private int sequence; //순서 컬럼


    //활동 내용 정보들
    private String title;

    private String category; //활동 카테고리











}