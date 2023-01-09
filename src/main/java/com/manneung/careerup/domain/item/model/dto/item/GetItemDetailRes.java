package com.manneung.careerup.domain.item.model.dto.item;


import com.manneung.careerup.domain.file.model.File;
import com.manneung.careerup.domain.item.model.Item;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class GetItemDetailRes {

    private String category;

    private String title;

    private String institution; //기관

    private String period;

    private String acquisition; //취득일

    private String field; //분야, 주제

    private String role; //맡은 역할

    private String content; //활동 내용

    private String realization; //느낀 점

    private List<File> files;

    public static GetItemDetailRes from(Item item, List<File> files){
        return GetItemDetailRes.builder()
                .category(item.getCategory())
                .title(item.getTitle())
                .institution(item.getInstitution())
                .period(item.getPeriod())
                .acquisition(item.getAcquisition())
                .field(item.getField())
                .role(item.getRole())
                .content(item.getContent())
                .realization(item.getRealization())
                .files(files)
                .build();
    }

}
