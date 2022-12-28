package com.manneung.careerup.domain.file.model;


import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_idx")
    private int fileIdx;


    @Column(name = "item_idx")
    private int itemIdx;


    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_name")
    private String fileName;
}



