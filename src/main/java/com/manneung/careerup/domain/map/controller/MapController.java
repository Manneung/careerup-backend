package com.manneung.careerup.domain.map.controller;


import com.manneung.careerup.domain.base.BaseResponse;
import com.manneung.careerup.domain.map.model.dto.*;
import com.manneung.careerup.domain.map.service.MapService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.manneung.careerup.domain.base.BaseResponseStatus.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/map")
public class MapController {
    private final MapService mapService;


    @ApiOperation(value = "커리어맵 생성", notes = "커리어맵 생성합니다")
    @PostMapping("")
    public ResponseEntity<BaseResponse<PostMapRes>> createMap(@RequestBody PostMapReq postMapReq){

        PostMapRes postMapRes = mapService.createMap(postMapReq);

        if(postMapRes == null){
            return ResponseEntity.ok(BaseResponse.ok(MAP_FAILED_TO_CREATE_ERROR));
        } else{
            return ResponseEntity.ok(BaseResponse.ok(SUCCESS, postMapRes));
        }
    }

    @ApiOperation(value = "커리어맵 수정", notes = "커리어맵 수정합니다")
    @PatchMapping("/{mapIdx}/modify")
    public ResponseEntity<BaseResponse<PatchMapRes>> modifyMap(@PathVariable int mapIdx, @RequestBody PatchMapReq patchMapReq){

        PatchMapRes patchMapRes = mapService.modifyMap(mapIdx, patchMapReq);

        if(patchMapRes == null){
            return ResponseEntity.ok(BaseResponse.ok(MAP_FAILED_TO_CREATE_ERROR));
        } else{
            return ResponseEntity.ok(BaseResponse.ok(SUCCESS, patchMapRes));
        }
    }


    @ApiOperation(value = "커리어맵 삭제", notes = "커리어맵 삭제합니다")
    @PatchMapping("/{mapIdx}/delete")
    public ResponseEntity<BaseResponse<DeleteMapRes>> deleteMap(@PathVariable int mapIdx){

        DeleteMapRes deleteMapRes = mapService.deleteMap(mapIdx);

        if(deleteMapRes == null){
            return ResponseEntity.ok(BaseResponse.ok(MAP_FAILED_TO_DELETE_ERROR));
        } else{
            return ResponseEntity.ok(BaseResponse.ok(SUCCESS, deleteMapRes));
        }
    }


    @ApiOperation(value = "현재 로그인한 회원의 커리어맵 리스트", notes = "현재 로그인한 회원의 커리어맵 리스트")
    @GetMapping("/my-map")
    public ResponseEntity<BaseResponse<List<GetMapSimpleRes>>> searchMyMap(){
        List<GetMapSimpleRes> getMapSimpleResList = mapService.searchMyMaps();
        return ResponseEntity.ok(BaseResponse.ok(SUCCESS, getMapSimpleResList));
    }



    @ApiOperation(value = "커리어맵idx로 맵 전체 정보 조회", notes = "커리어맵idx로 맵 전체 정보 조회")
    @GetMapping("/{mapIdx}")
    public ResponseEntity<BaseResponse<GetMapDetailRes>> searchMapDetail(@PathVariable int mapIdx){
        GetMapDetailRes getMapDetailRes = mapService.searchMapDetail(mapIdx);

        if(getMapDetailRes == null){
            return ResponseEntity.ok(BaseResponse.ok(MAP_NOT_FOUND_IDX_ERROR));

        } else {
            return ResponseEntity.ok(BaseResponse.ok(SUCCESS, getMapDetailRes));
        }
    }

}
