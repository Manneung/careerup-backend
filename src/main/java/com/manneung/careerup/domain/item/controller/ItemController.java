package com.manneung.careerup.domain.item.controller;


import com.manneung.careerup.domain.base.BaseResponse;
import com.manneung.careerup.domain.item.model.dto.GetItemDetailRes;
import com.manneung.careerup.domain.item.model.dto.PostItemReq;
import com.manneung.careerup.domain.item.model.dto.PostItemRes;
import com.manneung.careerup.domain.item.service.ItemService;
import com.manneung.careerup.domain.map.service.MapService;
import com.manneung.careerup.global.s3.S3UploaderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.manneung.careerup.domain.base.BaseResponseStatus.ITEM_NOT_FOUND_IDX_ERROR;
import static com.manneung.careerup.domain.base.BaseResponseStatus.SUCCESS;


@RequiredArgsConstructor
@RestController
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;
    private final S3UploaderService s3UploaderService;
    private final MapService mapService;


    @ApiOperation(value = "itemIdx로 활동 상세 내용 보기", notes = "itemIdx로 활동 상세 내용 보기")
    @GetMapping("")
    public ResponseEntity<BaseResponse<GetItemDetailRes>> showItemDetail(@RequestParam int itemIdx){
        GetItemDetailRes getItemDetailRes = itemService.showItemDetail(itemIdx);

        if(getItemDetailRes == null){
            return ResponseEntity.ok(BaseResponse.create(ITEM_NOT_FOUND_IDX_ERROR));
        } else {
            return ResponseEntity.ok(BaseResponse.create(SUCCESS, getItemDetailRes));
        }
    }

    @ApiOperation(value = "맵idx로 활동 추가하기", notes = "활동 추가하기(+버튼 눌렀을 때 생성")
    @PostMapping("")
    public ResponseEntity<BaseResponse<GetItemDetailRes>> createItem(@RequestParam int mapIdx, PostItemReq postItemReq){
        GetItemDetailRes getItemDetailRes = itemService.createItem(mapIdx, postItemReq);
        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getItemDetailRes));

    }



}
