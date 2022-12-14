package com.manneung.careerup.domain.map.controller;


import com.manneung.careerup.domain.base.BaseResponse;
import com.manneung.careerup.domain.map.model.dto.GetMapRes;
import com.manneung.careerup.domain.map.model.dto.PostMapReq;
import com.manneung.careerup.domain.map.model.dto.PostMapRes;
import com.manneung.careerup.domain.map.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.manneung.careerup.domain.base.BaseResponseStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/map")
public class MapController {
    private final MapService mapService;


    @PostMapping("")
    public ResponseEntity<BaseResponse<PostMapRes>> createMap(@RequestBody PostMapReq postMapReq){

        PostMapRes postMapRes = mapService.createMap(postMapReq);

        if(postMapRes == null){
            return ResponseEntity.ok(BaseResponse.create(MAP_FAILED_TO_CREATE_ERROR));
        } else{
            return ResponseEntity.ok(BaseResponse.create(SUCCESS, postMapRes));
        }
    }


    //닉네임 일치, 커리어맵 제목에 키워드 포함, --
    @GetMapping("")
    public ResponseEntity<BaseResponse<List<GetMapRes>>> searchMapsByNickname(@RequestParam String nickname){
        List<GetMapRes> getMapRes = mapService.searchMapsByNickname(nickname);

        if(getMapRes == null){
            return ResponseEntity.ok(BaseResponse.create(MAP_EMPTY_LIST_ERROR));
        } else{
            return ResponseEntity.ok(BaseResponse.create(SUCCESS, getMapRes));
        }
    }



}
