package com.manneung.careerup.domain.item.controller;


import com.fasterxml.jackson.databind.ser.Serializers;
import com.manneung.careerup.domain.base.BaseResponse;
import com.manneung.careerup.domain.file.service.FileService;
import com.manneung.careerup.domain.item.model.dto.Study.GetStudyRes;
import com.manneung.careerup.domain.item.model.dto.Study.PostStudyReq;
import com.manneung.careerup.domain.item.model.dto.certifcate.GetCertificateRes;
import com.manneung.careerup.domain.item.model.dto.certifcate.PostCertificateReq;
import com.manneung.careerup.domain.item.model.dto.club.GetClubRes;
import com.manneung.careerup.domain.item.model.dto.club.PostClubReq;
import com.manneung.careerup.domain.item.model.dto.contest.GetContestRes;
import com.manneung.careerup.domain.item.model.dto.contest.PostContestReq;
import com.manneung.careerup.domain.item.model.dto.etc.GetEtcRes;
import com.manneung.careerup.domain.item.model.dto.etc.PostEtcReq;
import com.manneung.careerup.domain.item.model.dto.external_activitiy.GetExternalActivityRes;
import com.manneung.careerup.domain.item.model.dto.external_activitiy.PostExternalActivityReq;
import com.manneung.careerup.domain.item.model.dto.item.GetItemDetailRes;
import com.manneung.careerup.domain.item.model.dto.item.PostItemReq;
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

    private final MapService mapService;
    private final FileService fileService;


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

    @ApiOperation(value="맵idx로 자격증 추가하기", notes = "자격증 추가하기")
    @PostMapping("/certificate")
    public ResponseEntity<BaseResponse<GetCertificateRes>> createCertificate(@RequestParam int mapIdx, PostCertificateReq postCertificateReq) {
        GetCertificateRes getCertificateRes = itemService.createCertificate(mapIdx, postCertificateReq);
        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getCertificateRes));
    }
    @ApiOperation(value = "맵idx로 동아리 추가하기", notes = "동아리 추가하기")
    @PostMapping("club")
    public ResponseEntity<BaseResponse<GetClubRes>> createClub(@RequestParam int mapIdx, PostClubReq postClubReq){
        GetClubRes getItemDetailRes = itemService.createClub(mapIdx,postClubReq);
        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getItemDetailRes));
    }
    @ApiOperation(value = "맵idx로 공모전 추가하기", notes = "공모전 추가하기")
    @PostMapping("/contest")
    public ResponseEntity<BaseResponse<GetContestRes>> createContest(@RequestParam int mapIdx, PostContestReq postContestReq){
        GetContestRes getContestRes = itemService.createContest(mapIdx, postContestReq);
        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getContestRes));
    }
    @ApiOperation(value = "맵idx로 기타 활동 추가하기", notes = "기타 활동 추가하기")
    @PostMapping("/etc")
    public ResponseEntity<BaseResponse<GetEtcRes>> createEtc(@RequestParam int mapIdx, PostEtcReq postEtcReq){
        GetEtcRes getEtcRes = itemService.createEtc(mapIdx, postEtcReq);
        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getEtcRes));
    }
    @ApiOperation(value = "맵idx로 대외활동 추가하기", notes = "대외활동 추가하기")
    @PostMapping("/external_activity")
    public ResponseEntity<BaseResponse<GetExternalActivityRes>> createExternalActivity(@RequestParam int mapIdx, PostExternalActivityReq postExternalActivityReq){
        GetExternalActivityRes getExternalActivityRes = itemService.createExternalActivity(mapIdx, postExternalActivityReq);
        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getExternalActivityRes));
    }
    @ApiOperation(value = "맵idx로 스터디 추가하기", notes = "스터디 추가하기")
    @PostMapping("/study")
    public ResponseEntity<BaseResponse<GetStudyRes>> createStudy(@RequestParam int mapIdx, PostStudyReq postStudyReq){
        GetStudyRes getStudyRes = itemService.createStudy(mapIdx, postStudyReq);
        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getStudyRes));
    }



    @ApiOperation(value = "아이템에 활동 사진 추가", notes = "아이템에 활동 사진 추가")
    @PostMapping("/upload/{itemIdx}/picture")
    public String itemPictureUpload(
            @RequestPart("data") MultipartFile multipartFile,
            @PathVariable int itemIdx) throws IOException {

        return fileService.itemPictureUpload(itemIdx, multipartFile, "careerup-bucket", "image" );
    }



}
