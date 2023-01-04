package com.manneung.careerup.domain.item.model.dto.contest;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class PostContestReq {
    //공모전 요청

    private int sequence; //순서

    //private String category;

    private String title;

    private String institution; //기관

    private String period;

    private String field; //분야, 주제

    private String content; //활동 내용

    private String realization; //느낀 점
}
