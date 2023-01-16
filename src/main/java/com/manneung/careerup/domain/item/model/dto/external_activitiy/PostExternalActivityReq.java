package com.manneung.careerup.domain.item.model.dto.external_activitiy;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class PostExternalActivityReq {
    //대외활동 요청

    private int sequence; //순서

    //private String category;

    private String title;

    private String subtitle; //활동 부제목

    private String institution; //기관

    private String period;

    private String field; //분야, 주제

    private String content; //활동 내용

    private String realization; //느낀 점
}
