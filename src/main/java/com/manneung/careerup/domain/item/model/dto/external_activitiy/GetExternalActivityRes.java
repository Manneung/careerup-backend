package com.manneung.careerup.domain.item.model.dto.external_activitiy;


import com.manneung.careerup.domain.item.model.Item;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class GetExternalActivityRes {
    //대외활동 응답

    private int itemIdx;

    private String category;

    private String title;

    private String subtitle; //활동 부제목

    private String institution; //기관

    private String period;

    private String field; //분야, 주제

    private String content; //활동 내용

    private String realization; //느낀 점

    public static GetExternalActivityRes from(Item item){
        return GetExternalActivityRes.builder()
                .itemIdx(item.getItemIdx())
                .category(item.getCategory())
                .title(item.getTitle())
                .subtitle(item.getSubtitle())
                .institution(item.getInstitution())
                .period(item.getPeriod())
                .field(item.getField())
                .content(item.getContent())
                .realization(item.getRealization())
                .build();
    }

}
