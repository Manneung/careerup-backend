package com.manneung.careerup.domain.map.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.manneung.careerup.domain.item.model.dto.PostItemReq;
import lombok.Getter;
import lombok.Setter;

import java.util.List;



@Setter
@Getter
@JsonPropertyOrder({"nickname", "title", "job", "itemList"})
public class PostMapReq {

    //private Integer userIdx;
    private String nickname;
    private String title;
    private String job;
    private List<PostItemReq> itemList;

}
