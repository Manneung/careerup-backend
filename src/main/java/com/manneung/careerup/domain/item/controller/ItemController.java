package com.manneung.careerup.domain.item.controller;


import com.manneung.careerup.domain.base.BaseResponse;
import com.manneung.careerup.domain.file.service.FileService;
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
import com.manneung.careerup.domain.item.model.dto.item.GetItemRes;
import com.manneung.careerup.domain.item.model.dto.item.PatchSequenceReq;
import com.manneung.careerup.domain.item.model.dto.item.PostItemReq;
import com.manneung.careerup.domain.item.model.dto.study.GetStudyRes;
import com.manneung.careerup.domain.item.model.dto.study.PostStudyReq;
import com.manneung.careerup.domain.item.service.ItemService;
import com.manneung.careerup.domain.map.service.MapService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.manneung.careerup.domain.base.BaseResponseStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/item")
public class ItemController {
    private final ItemService itemService;

    private final MapService mapService;
    private final FileService fileService;


    @ApiOperation(value = "itemIdx로 활동 상세 내용 보기", notes = "itemIdx로 활동 상세 내용 보기")
    @GetMapping("/{itemIdx}/detail")
    public ResponseEntity<BaseResponse<GetItemDetailRes>> showItemDetail(@PathVariable(name = "itemIdx") int itemIdx) {
        GetItemDetailRes getItemDetailRes = itemService.showItemDetail(itemIdx);

        if (getItemDetailRes == null) {
            return ResponseEntity.ok(BaseResponse.ok(ITEM_NOT_FOUND_IDX_ERROR));
        } else {
            return ResponseEntity.ok(BaseResponse.ok(SUCCESS, getItemDetailRes));
        }
    }


    //아이템 추가하기 api
    @ApiOperation(value = "맵idx로 자격증 추가하기", notes = "자격증 추가하기")
    @PostMapping("/certificate")
    public ResponseEntity<BaseResponse<GetCertificateRes>> createCertificate(
            @RequestParam(name = "mapIdx") int mapIdx, @RequestBody PostCertificateReq postCertificateReq) {

        GetCertificateRes getCertificateRes = itemService.createCertificate(mapIdx, postCertificateReq);

        if (getCertificateRes == null)
            return ResponseEntity.ok(BaseResponse.ok(MAP_NOT_FOUND_IDX_ERROR));

        return ResponseEntity.ok(BaseResponse.ok(SUCCESS, getCertificateRes));
    }

    @ApiOperation(value = "맵idx로 동아리 추가하기", notes = "동아리 추가하기")
    @PostMapping("/club")
    public ResponseEntity<BaseResponse<GetClubRes>> createClub(
            @RequestParam(name = "mapIdx") int mapIdx, @RequestBody PostClubReq postClubReq) {

        GetClubRes getClubRes = itemService.createClub(mapIdx, postClubReq);

        if (getClubRes == null)
            return ResponseEntity.ok(BaseResponse.ok(MAP_NOT_FOUND_IDX_ERROR));

        return ResponseEntity.ok(BaseResponse.ok(SUCCESS, getClubRes));
    }

    @ApiOperation(value = "맵idx로 공모전 추가하기", notes = "공모전 추가하기")
    @PostMapping("/contest")
    public ResponseEntity<BaseResponse<GetContestRes>> createContest(
            @RequestParam(name = "mapIdx") int mapIdx, @RequestBody PostContestReq postContestReq) {

        GetContestRes getContestRes = itemService.createContest(mapIdx, postContestReq);

        if (getContestRes == null)
            return ResponseEntity.ok(BaseResponse.ok(MAP_NOT_FOUND_IDX_ERROR));

        return ResponseEntity.ok(BaseResponse.ok(SUCCESS, getContestRes));
    }

    @ApiOperation(value = "맵idx로 기타 활동 추가하기", notes = "기타 활동 추가하기")
    @PostMapping("/etc")
    public ResponseEntity<BaseResponse<GetEtcRes>> createEtc(
            @RequestParam(name = "mapIdx") int mapIdx, @RequestBody PostEtcReq postEtcReq) {

        GetEtcRes getEtcRes = itemService.createEtc(mapIdx, postEtcReq);

        if (getEtcRes == null)
            return ResponseEntity.ok(BaseResponse.ok(MAP_NOT_FOUND_IDX_ERROR));

        return ResponseEntity.ok(BaseResponse.ok(SUCCESS, getEtcRes));
    }

    @ApiOperation(value = "맵idx로 대외활동 추가하기", notes = "대외활동 추가하기")
    @PostMapping("/external-activity")
    public ResponseEntity<BaseResponse<GetExternalActivityRes>> createExternalActivity(
            @RequestParam(name = "mapIdx") int mapIdx, @RequestBody PostExternalActivityReq postExternalActivityReq) {

        GetExternalActivityRes getExternalActivityRes = itemService.createExternalActivity(mapIdx, postExternalActivityReq);

        if (getExternalActivityRes == null)
            return ResponseEntity.ok(BaseResponse.ok(MAP_NOT_FOUND_IDX_ERROR));

        return ResponseEntity.ok(BaseResponse.ok(SUCCESS, getExternalActivityRes));
    }

    @ApiOperation(value = "맵idx로 스터디 추가하기", notes = "스터디 추가하기")
    @PostMapping("/study")
    public ResponseEntity<BaseResponse<GetStudyRes>> createStudy(
            @RequestParam(name = "mapIdx") int mapIdx, @RequestBody PostStudyReq postStudyReq) {


        GetStudyRes getStudyRes = itemService.createStudy(mapIdx, postStudyReq);

        if (getStudyRes == null)
            return ResponseEntity.ok(BaseResponse.ok(MAP_NOT_FOUND_IDX_ERROR));

        return ResponseEntity.ok(BaseResponse.ok(SUCCESS, getStudyRes));
    }


    //아이템 내용만 수정하기 api
    @ApiOperation(value = "아이템idx로 아이템 내용만 수정하기(카테고리로 구분, 순서변경은 따로)", notes = "아이템 수정하기")
    @PatchMapping("/{itemIdx}/modify")
    public ResponseEntity<BaseResponse<Object>> modifyItem(
            @PathVariable(name = "itemIdx") int itemIdx, @RequestBody PostItemReq postItemReq) {

        if (postItemReq.getCategory().equals("자격증")) {
            GetCertificateRes getCertificateRes = itemService.modifyCertificate(itemIdx, postItemReq);
            return ResponseEntity.ok(BaseResponse.ok(SUCCESS, getCertificateRes));
        } else if (postItemReq.getCategory().equals("동아리")) {
            GetClubRes getItemDetailRes = itemService.modifyClub(itemIdx, postItemReq);
            return ResponseEntity.ok(BaseResponse.ok(SUCCESS, getItemDetailRes));
        } else if (postItemReq.getCategory().equals("공모전")) {
            GetContestRes getContestRes = itemService.modifyContest(itemIdx, postItemReq);
            return ResponseEntity.ok(BaseResponse.ok(SUCCESS, getContestRes));
        } else if (postItemReq.getCategory().equals("기타")) {
            GetEtcRes getEtcRes = itemService.modifyEtc(itemIdx, postItemReq);
            return ResponseEntity.ok(BaseResponse.ok(SUCCESS, getEtcRes));
        } else if (postItemReq.getCategory().equals("대외활동")) {
            GetExternalActivityRes getExternalActivityRes = itemService.modifyExternalActivity(itemIdx, postItemReq);
            return ResponseEntity.ok(BaseResponse.ok(SUCCESS, getExternalActivityRes));
        } else if (postItemReq.getCategory().equals("스터디")) {
            GetStudyRes getStudyRes = itemService.modifyStudy(itemIdx, postItemReq);
            return ResponseEntity.ok(BaseResponse.ok(SUCCESS, getStudyRes));
        }

        return ResponseEntity.ok(BaseResponse.ok(ITEM_NOT_FOUND_IDX_ERROR));
    }

    @ApiOperation(value = "맵idx로 아이템 순서 변경 ", notes = "맵idx로 아이템 순서 변경")
    @PatchMapping("/{mapIdx}")
    public ResponseEntity<BaseResponse<List<GetItemRes>>> showItemDetail(
            @PathVariable(name = "mapIdx") int mapIdx, @RequestBody List<PatchSequenceReq> sequenceReqList) {

        if (!mapService.existsMapByMapIdx(mapIdx))
            return ResponseEntity.ok(BaseResponse.ok(MAP_NOT_FOUND_IDX_ERROR));

        List<GetItemRes> getItemResList = itemService.changeItemSequence(mapIdx, sequenceReqList);

        return ResponseEntity.ok(BaseResponse.ok(SUCCESS, getItemResList));
    }


    @ApiOperation(value = "아이템 idx로 삭제하기", notes = "아이템 idx로 삭제하기")
    @DeleteMapping("/{itemIdx}")
    public ResponseEntity<BaseResponse<String>> deleteItem(@PathVariable(name = "itemIdx") int itemIdx) {
        String result = itemService.deleteItem(itemIdx);
        return ResponseEntity.ok(BaseResponse.ok(SUCCESS, result));
    }

    @ApiOperation(value = "아이템에 활동 사진 추가", notes = "아이템에 활동 사진 추가")
    @PostMapping("/upload/{itemIdx}/picture")
    public ResponseEntity<BaseResponse<List<String>>> itemPictureUpload(
            @RequestPart("images") List<MultipartFile> multipartFiles, @PathVariable int itemIdx) throws IOException {

        if (!itemService.existsByItemIdx(itemIdx))
            return ResponseEntity.ok(BaseResponse.ok(ITEM_NOT_FOUND_IDX_ERROR));

        List<String> list = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            String fileUrl = fileService.itemPictureUpload(itemIdx, file, "careerup-bucket", "images", "picture");
            list.add(fileUrl);
        }
        return ResponseEntity.ok(BaseResponse.ok(SUCCESS, list));
    }

    @ApiOperation(value = "아이템에 파일 추가", notes = "아이템에 파일 추가")
    @PostMapping("upload/{itemIdx}/files")
    public ResponseEntity<BaseResponse<List<String>>> itemFileUpload(
            @RequestPart("files") List<MultipartFile> multipartFiles, @PathVariable int itemIdx, @RequestPart String title) throws IOException {

        if (!itemService.existsByItemIdx(itemIdx))
            return ResponseEntity.ok(BaseResponse.ok(ITEM_NOT_FOUND_IDX_ERROR));


        List<String> list = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            String fileUrl = fileService.itemPictureUpload(itemIdx, file, "careerup-bucket", "files", title);
            list.add(fileUrl);
        }
        return ResponseEntity.ok(BaseResponse.ok(SUCCESS, list));
    }

    @ApiOperation(value = "아이템에 있는 파일 또는 사진 삭제", notes = "아이템에 있는 파일을 삭제합니다.")
    @DeleteMapping("delete/{fileIdx}")
    public ResponseEntity<BaseResponse<Boolean>> deleteFile(@PathVariable int fileIdx) {
        return ResponseEntity.ok(BaseResponse.ok(SUCCESS, fileService.deleteFile(fileIdx)));
    }
}
