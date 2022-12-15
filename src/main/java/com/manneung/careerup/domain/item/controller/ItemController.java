package com.manneung.careerup.domain.item.controller;


import com.manneung.careerup.domain.base.BaseResponse;
import com.manneung.careerup.domain.item.model.dto.GetItemDetailRes;
import com.manneung.careerup.domain.item.service.ItemService;
import com.manneung.careerup.domain.map.service.MapService;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import static com.manneung.careerup.domain.base.BaseResponseStatus.ITEM_NOT_FOUND_IDX_ERROR;
import static com.manneung.careerup.domain.base.BaseResponseStatus.SUCCESS;


@RequiredArgsConstructor
@RestController
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;
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





}
