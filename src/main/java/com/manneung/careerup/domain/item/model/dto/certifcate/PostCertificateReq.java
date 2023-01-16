package com.manneung.careerup.domain.item.model.dto.certifcate;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class PostCertificateReq {
    //자격증 요청

    private int sequence; //순서

    //private String category;

    private String title;

    private String subtitle; //활동 부제목

    private String institution; //기관

    private String period;

    private String acquisition; //취득일

    private String content; //활동 내용

    private String realization; //느낀 점
}
