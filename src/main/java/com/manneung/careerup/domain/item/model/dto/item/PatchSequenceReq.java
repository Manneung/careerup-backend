package com.manneung.careerup.domain.item.model.dto.item;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PatchSequenceReq {
    //순서변경을 위한 요청 클래스
    //itemIdx와 sequence 매핑?
    private int itemIdx;
    private int sequence;
}
