package com.manneung.careerup.domain.item.model.dto.external_activitiy;


import com.manneung.careerup.domain.item.model.Item;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class GetExternalActivityRes {

    private String category;

    private String title;

    private String institution; //기관

    private String period;

    private String field; //분야, 주제

    private String content; //활동 내용

    private String realization; //느낀 점

    public static GetExternalActivityRes from(Item item){
        return GetExternalActivityRes.builder()
                .category(item.getCategory())
                .title(item.getTitle())
                .institution(item.getInstitution())
                .period(item.getPeriod())
                .field(item.getField())
                .content(item.getContent())
                .realization(item.getRealization())
                .build();
    }

}
