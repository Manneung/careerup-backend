package com.manneung.careerup.domain.map.controller;


import com.manneung.careerup.domain.base.BaseResponse;
import com.manneung.careerup.domain.map.model.dto.GetMapDetailRes;
import com.manneung.careerup.domain.map.model.dto.GetMapRes;
import com.manneung.careerup.domain.map.model.dto.PostMapReq;
import com.manneung.careerup.domain.map.model.dto.PostMapRes;
import com.manneung.careerup.domain.map.service.MapService;
import io.swagger.annotations.ApiOperation;
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


    @ApiOperation(value = "커리어맵 생성(커리어맵, 아이템)", notes = "커리어맵 생성합니다")
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
    @ApiOperation(value = "닉네임으로 커리어맵 전부 찾기 -v2", notes = "닉네임을 커리어맵을 전부 찾습니다.")
    @GetMapping("")
    public ResponseEntity<BaseResponse<List<GetMapRes>>> searchMapsByNickname(
                                    @RequestParam(value = "nickname", required = false) String nickname){
        List<GetMapRes> getMapRes = mapService.searchMapsByNickname(nickname);

        if(getMapRes == null){
            return ResponseEntity.ok(BaseResponse.create(MAP_EMPTY_LIST_ERROR));
        } else{
            return ResponseEntity.ok(BaseResponse.create(SUCCESS, getMapRes));
        }
    }

    @ApiOperation(value = "커리어맵 idx로 맵의 간략한 정보를 출력", notes = "커리어맵 idx로 맵의 간략한 정보를 출력합니다.")
    @GetMapping("{mapIdx}")
    public ResponseEntity<BaseResponse<GetMapDetailRes>> searchMapDetail(@PathVariable int mapIdx){
        GetMapDetailRes getMapDetailRes = mapService.searchMapDetail(mapIdx);

        if(getMapDetailRes == null){
            return ResponseEntity.ok(BaseResponse.create(MAP_NOT_FOUND_IDX_ERROR));

        } else {
            return ResponseEntity.ok(BaseResponse.create(SUCCESS, getMapDetailRes));
        }
    }






}
