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

    @ApiOperation(value = "커리어맵 수정", notes = "커리어맵 수정합니다")
    @PatchMapping("/{mapIdx}/modify")
    public ResponseEntity<BaseResponse<PatchMapRes>> modifyMap(@PathVariable int mapIdx, @RequestBody PatchMapReq patchMapReq){

        PatchMapRes patchMapRes = mapService.modifyMap(mapIdx, patchMapReq);

        if(patchMapRes == null){
            return ResponseEntity.ok(BaseResponse.create(MAP_FAILED_TO_CREATE_ERROR));
        } else{
            return ResponseEntity.ok(BaseResponse.create(SUCCESS, patchMapRes));
        }
    }


    @ApiOperation(value = "커리어맵 삭제", notes = "커리어맵 삭제합니다")
    @PatchMapping("/{mapIdx}/delete")
    public ResponseEntity<BaseResponse<DeleteMapRes>> deleteMap(@PathVariable int mapIdx){

        DeleteMapRes deleteMapRes = mapService.deleteMap(mapIdx);

        if(deleteMapRes == null){
            return ResponseEntity.ok(BaseResponse.create(MAP_FAILED_TO_DELETE_ERROR));
        } else{
            return ResponseEntity.ok(BaseResponse.create(SUCCESS, deleteMapRes));
        }
    }




    //닉네임 일치, 커리어맵 제목에 키워드 포함, --
    @ApiOperation(value = "닉네임으로 커리어맵 전부 찾기", notes = "닉네임으로 커리어맵을 전부 찾습니다.")
    @GetMapping("/list/{nickname}")
    public ResponseEntity<BaseResponse<List<GetMapRes>>> searchMapsByNickname(@PathVariable String nickname){
        List<GetMapRes> getMapRes = null;

        if(nickname != null){
            getMapRes = mapService.searchMapsByNickname(nickname);
        }

        if(getMapRes == null){
            return ResponseEntity.ok(BaseResponse.create(MAP_EMPTY_LIST_ERROR));
        } else{
            return ResponseEntity.ok(BaseResponse.create(SUCCESS, getMapRes));
        }
    }


    @ApiOperation(value = "제목, 직업으로 커리어맵 전부 찾기", notes = "제목, 직업으로 커리어맵 전부 찾기")
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<List<GetMapRes>>> searchMaps(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "job", required = false) String job
    ){
        List<GetMapRes> getMapRes = null;

        if(title != null){
            getMapRes = mapService.searchMapsByTitle(title);
        } else if(job != null){
            getMapRes = mapService.searchMapsByJob(job);
        }



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
