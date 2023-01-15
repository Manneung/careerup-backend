package com.manneung.careerup.domain.item.model.dto.etc;


import com.manneung.careerup.domain.item.model.Item;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class GetEtcRes {
    //기타 응답
    private int itemIdx;

    private int itemIdx;

    private String category;

    private String title;

    private String period;

    private String role; //맡은 역할

    private String content; //활동 내용

    private String realization; //느낀 점

    public static GetEtcRes from(Item item){
        return GetEtcRes.builder()
                .itemIdx(item.getItemIdx())
                .category(item.getCategory())
                .title(item.getTitle())
                .period(item.getPeriod())
                .role(item.getRole())
                .content(item.getContent())
                .realization(item.getRealization())
                .build();
    }

}

