package com.manneung.careerup.domain.item.model.dto.Society;


import com.manneung.careerup.domain.item.model.Item;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class GetSocietyRes {
    private String category;

    private String title;

    private String period;

    private String role; //맡은 역할

    private String content; //활동 내용

    private String realization; //느낀 점

    public static GetSocietyRes from(Item item){
        return GetSocietyRes.builder()
                .category(item.getCategory())
                .title(item.getTitle())
                .period(item.getPeriod())
                .role(item.getRole())
                .content(item.getContent())
                .realization(item.getRealization())
                .build();
    }

}

