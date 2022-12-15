package com.manneung.careerup.domain.item.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GetItemRes { //커리어맵에서 제목만 간단하게 보여줌
    private String title;
    private int sequence;
}
