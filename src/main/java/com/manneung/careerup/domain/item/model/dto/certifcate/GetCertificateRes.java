package com.manneung.careerup.domain.item.model.dto.certifcate;


import com.manneung.careerup.domain.item.model.Item;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class GetCertificateRes {
    //자격증 응답
    private int itemIdx;

    private String category;

    private String title;

    private String subtitle; //활동 부제목

    private String institution; //기관

    private String period;

    private String acquisition; //취득일

    private String content; //활동 내용

    private String realization; //느낀 점

    public static GetCertificateRes from(Item item){
        return GetCertificateRes.builder()
                .itemIdx(item.getItemIdx())
                .category(item.getCategory())
                .title(item.getTitle())
                .subtitle(item.getSubtitle())
                .institution(item.getInstitution())
                .period(item.getPeriod())
                .acquisition(item.getAcquisition())
                .content(item.getContent())
                .realization(item.getRealization())
                .build();
    }

}
