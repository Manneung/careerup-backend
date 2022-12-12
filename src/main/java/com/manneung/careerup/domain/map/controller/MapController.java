package com.manneung.careerup.domain.map.controller;


import com.manneung.careerup.domain.base.BaseResponse;
import com.manneung.careerup.domain.map.model.dto.PostMapReq;
import com.manneung.careerup.domain.map.model.dto.PostMapRes;
import com.manneung.careerup.domain.map.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/map")
public class MapController {
    private final MapService mapService;


//    @PostMapping()
//    public BaseResponse<PostMapRes> createMap(@RequestBody PostMapReq postMapReq){
//
//        PostMapRes postMapRes = mapService.createMap(postMapReq);
//
//
//        return new BaseResponse<>(postMapRes);
//    }



}
