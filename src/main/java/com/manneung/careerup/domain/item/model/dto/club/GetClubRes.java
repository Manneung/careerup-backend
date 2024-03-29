package com.manneung.careerup.domain.item.model.dto.club;


import com.manneung.careerup.domain.item.model.Item;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class GetClubRes {
    //동아리 응답
    private int itemIdx;

    private String category;

    private String title;

    private String subtitle; //활동 부제목

    private String period;

    private String role; //맡은 역할

    private String content; //활동 내용

    private String realization; //느낀 점

    public static GetClubRes from(Item item){
        return GetClubRes.builder()
                .itemIdx(item.getItemIdx())
                .category(item.getCategory())
                .title(item.getTitle())
                .subtitle(item.getSubtitle())
                .period(item.getPeriod())
                .role(item.getRole())
                .content(item.getContent())
                .realization(item.getRealization())
                .build();
    }

}

