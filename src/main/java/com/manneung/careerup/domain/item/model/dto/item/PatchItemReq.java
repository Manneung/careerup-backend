package com.manneung.careerup.domain.item.model.dto.item;


import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PatchItemReq {
    //아이템 수정

    //private int sequence;

    private String category;

    private String title; //활동 제목

    private String subtitle; //활동 부제목

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
