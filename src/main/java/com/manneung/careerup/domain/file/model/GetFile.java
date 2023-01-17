package com.manneung.careerup.domain.file.model;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class GetFile {

    private int fileIdx;
    private String title;
    private String fileType;
    private String fileUrl;

}
