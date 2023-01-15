package com.manneung.careerup.domain.item.model.dto.study;


import com.manneung.careerup.domain.item.model.Item;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class GetStudyRes {
    //스터디 응답

    private int itemIdx;

    private String category;

    private String title;

    private String period;

    private String field; //분야, 주제

    private String content; //활동 내용

    private String realization; //느낀 점

    public static GetStudyRes from(Item item){
        return GetStudyRes.builder()
                .itemIdx(item.getItemIdx())
                .category(item.getCategory())
                .title(item.getTitle())
                .period(item.getPeriod())
                .field(item.getField())
                .content(item.getContent())
                .realization(item.getRealization())
                .build();
    }

}
