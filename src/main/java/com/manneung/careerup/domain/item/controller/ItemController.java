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

import static com.manneung.careerup.domain.base.BaseResponseStatus.ITEM_NOT_FOUND_IDX_ERROR;
import static com.manneung.careerup.domain.base.BaseResponseStatus.SUCCESS;


@RequiredArgsConstructor
@RestController
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    private final MapService mapService;
    private final FileService fileService;


    @ApiOperation(value = "itemIdx??? ?????? ?????? ?????? ??????", notes = "itemIdx??? ?????? ?????? ?????? ??????")
    @GetMapping("/{itemIdx}/detail")
    public ResponseEntity<BaseResponse<GetItemDetailRes>> showItemDetail(@PathVariable(name = "itemIdx") int itemIdx) {
        GetItemDetailRes getItemDetailRes = itemService.showItemDetail(itemIdx);

        if (getItemDetailRes == null) {
            return ResponseEntity.ok(BaseResponse.create(ITEM_NOT_FOUND_IDX_ERROR));
        } else {
            return ResponseEntity.ok(BaseResponse.create(SUCCESS, getItemDetailRes));
        }
    }


    //????????? ???????????? api
    @ApiOperation(value = "???idx??? ????????? ????????????", notes = "????????? ????????????")
    @PostMapping("/certificate")
    public ResponseEntity<BaseResponse<GetCertificateRes>> createCertificate(
            @RequestParam(name = "mapIdx") int mapIdx, @RequestBody PostCertificateReq postCertificateReq) {
        GetCertificateRes getCertificateRes = itemService.createCertificate(mapIdx, postCertificateReq);
        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getCertificateRes));
    }

    @ApiOperation(value = "???idx??? ????????? ????????????", notes = "????????? ????????????")
    @PostMapping("/club")
    public ResponseEntity<BaseResponse<GetClubRes>> createClub(
            @RequestParam(name = "mapIdx") int mapIdx, @RequestBody PostClubReq postClubReq) {
        GetClubRes getItemDetailRes = itemService.createClub(mapIdx, postClubReq);
        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getItemDetailRes));
    }

    @ApiOperation(value = "???idx??? ????????? ????????????", notes = "????????? ????????????")
    @PostMapping("/contest")
    public ResponseEntity<BaseResponse<GetContestRes>> createContest(
            @RequestParam(name = "mapIdx") int mapIdx, @RequestBody PostContestReq postContestReq) {
        GetContestRes getContestRes = itemService.createContest(mapIdx, postContestReq);
        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getContestRes));
    }

    @ApiOperation(value = "???idx??? ?????? ?????? ????????????", notes = "?????? ?????? ????????????")
    @PostMapping("/etc")
    public ResponseEntity<BaseResponse<GetEtcRes>> createEtc(
            @RequestParam(name = "mapIdx") int mapIdx, @RequestBody PostEtcReq postEtcReq) {
        GetEtcRes getEtcRes = itemService.createEtc(mapIdx, postEtcReq);
        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getEtcRes));
    }

    @ApiOperation(value = "???idx??? ???????????? ????????????", notes = "???????????? ????????????")
    @PostMapping("/external-activity")
    public ResponseEntity<BaseResponse<GetExternalActivityRes>> createExternalActivity(
            @RequestParam(name = "mapIdx") int mapIdx, @RequestBody PostExternalActivityReq postExternalActivityReq) {
        GetExternalActivityRes getExternalActivityRes = itemService.createExternalActivity(mapIdx, postExternalActivityReq);
        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getExternalActivityRes));
    }

    @ApiOperation(value = "???idx??? ????????? ????????????", notes = "????????? ????????????")
    @PostMapping("/study")
    public ResponseEntity<BaseResponse<GetStudyRes>> createStudy(
            @RequestParam(name = "mapIdx") int mapIdx, @RequestBody PostStudyReq postStudyReq) {
        GetStudyRes getStudyRes = itemService.createStudy(mapIdx, postStudyReq);
        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getStudyRes));
    }


    //????????? ????????? ???????????? api
    @ApiOperation(value = "???idx??? ????????? ????????? ????????????(??????????????? ??????, ??????????????? ??????)", notes = "????????? ????????????")
    @PatchMapping("/{itemIdx}/modify")
    public ResponseEntity<BaseResponse<Object>> modifyItem(
            @PathVariable(name = "itemIdx") int itemIdx, @RequestBody PostItemReq postItemReq) {

        if (postItemReq.getCategory().equals("?????????")) {
            GetCertificateRes getCertificateRes = itemService.modifyCertificate(itemIdx, postItemReq);
            return ResponseEntity.ok(BaseResponse.create(SUCCESS, getCertificateRes));
        } else if (postItemReq.getCategory().equals("?????????")) {
            GetClubRes getItemDetailRes = itemService.modifyClub(itemIdx, postItemReq);
            return ResponseEntity.ok(BaseResponse.create(SUCCESS, getItemDetailRes));
        } else if (postItemReq.getCategory().equals("?????????")) {
            GetContestRes getContestRes = itemService.modifyContest(itemIdx, postItemReq);
            return ResponseEntity.ok(BaseResponse.create(SUCCESS, getContestRes));
        } else if (postItemReq.getCategory().equals("??????")) {
            GetEtcRes getEtcRes = itemService.modifyEtc(itemIdx, postItemReq);
            return ResponseEntity.ok(BaseResponse.create(SUCCESS, getEtcRes));
        } else if (postItemReq.getCategory().equals("????????????")) {
            GetExternalActivityRes getExternalActivityRes = itemService.modifyExternalActivity(itemIdx, postItemReq);
            return ResponseEntity.ok(BaseResponse.create(SUCCESS, getExternalActivityRes));
        } else if (postItemReq.getCategory().equals("?????????")) {
            GetStudyRes getStudyRes = itemService.modifyStudy(itemIdx, postItemReq);
            return ResponseEntity.ok(BaseResponse.create(SUCCESS, getStudyRes));
        }

        return ResponseEntity.ok(BaseResponse.create(ITEM_NOT_FOUND_IDX_ERROR));
    }

    @ApiOperation(value = "???idx??? ????????? ?????? ?????? ", notes = "???idx??? ????????? ?????? ??????")
    @PatchMapping("/{mapIdx}")
    public ResponseEntity<BaseResponse<List<GetItemRes>>> showItemDetail(
            @PathVariable(name = "mapIdx") int mapIdx, @RequestBody List<PatchSequenceReq> sequenceReqList) {
        List<GetItemRes> getItemResList = itemService.changeItemSequence(mapIdx, sequenceReqList);

        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getItemResList));
    }


    @ApiOperation(value = "????????? idx??? ????????????", notes = "????????? idx??? ????????????")
    @DeleteMapping("/{itemIdx}")
    public ResponseEntity<BaseResponse<String>> deleteItem(@PathVariable(name = "itemIdx") int itemIdx) {
        String result = itemService.deleteItem(itemIdx);
        return ResponseEntity.ok(BaseResponse.create(SUCCESS, result));
    }

//    @ApiOperation(value="???idx??? ????????? ????????????", notes = "????????? ????????????")
//    @PatchMapping("/{itemIdx}/certificate")
//    public ResponseEntity<BaseResponse<GetCertificateRes>> modifyCertificate(@PathVariable(name = "itemIdx") int itemIdx, PatchCertificateReq patchCertificateReq) {
//        GetCertificateRes getCertificateRes = itemService.modifyCertificate(itemIdx, patchCertificateReq);
//        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getCertificateRes));
//    }
//    @ApiOperation(value = "???idx??? ????????? ????????????", notes = "????????? ????????????")
//    @PatchMapping("/{itemIdx}/club")
//    public ResponseEntity<BaseResponse<GetClubRes>> modifyClub(@PathVariable(name = "itemIdx") int itemIdx, PatchClubReq patchClubReq){
//        GetClubRes getItemDetailRes = itemService.modifyClub(itemIdx,patchClubReq);
//        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getItemDetailRes));
//    }
//    @ApiOperation(value = "???idx??? ????????? ????????????", notes = "????????? ????????????")
//    @PatchMapping("/{itemIdx}/contest")
//    public ResponseEntity<BaseResponse<GetContestRes>> modifyContest(@PathVariable(name = "itemIdx") int itemIdx, PatchContestReq patchContestReq){
//        GetContestRes getContestRes = itemService.modifyContest(itemIdx, patchContestReq);
//        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getContestRes));
//    }
//    @ApiOperation(value = "???idx??? ?????? ?????? ????????????", notes = "?????? ?????? ????????????")
//    @PatchMapping("/{itemIdx}/etc")
//    public ResponseEntity<BaseResponse<GetEtcRes>> modifyEtc(@PathVariable(name = "itemIdx") int itemIdx, PatchEtcReq patchEtcReq){
//        GetEtcRes getEtcRes = itemService.modifyEtc(itemIdx, patchEtcReq);
//        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getEtcRes));
//    }
//    @ApiOperation(value = "???idx??? ???????????? ????????????", notes = "???????????? ????????????")
//    @PatchMapping("/{itemIdx}/external-activity")
//    public ResponseEntity<BaseResponse<GetExternalActivityRes>> modifyExternalActivity(@PathVariable(name = "itemIdx") int itemIdx, PatchExternalActivityReq patchExternalActivityReq){
//        GetExternalActivityRes getExternalActivityRes = itemService.modifyExternalActivity(itemIdx, patchExternalActivityReq);
//        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getExternalActivityRes));
//    }
//    @ApiOperation(value = "???idx??? ????????? ????????????", notes = "????????? ????????????")
//    @PatchMapping("/{itemIdx}/study")
//    public ResponseEntity<BaseResponse<GetStudyRes>> modifyStudy(@PathVariable(name = "itemIdx") int itemIdx, PatchStudyReq patchStudyReq){
//        GetStudyRes getStudyRes = itemService.modifyStudy(itemIdx, patchStudyReq);
//        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getStudyRes));
//    }


//    @ApiOperation(value = "???idx??? ?????? ????????????(????????????)", notes = "?????? ????????????(+?????? ????????? ??? ??????")
//    @PostMapping("")
//    public ResponseEntity<BaseResponse<GetItemDetailRes>> createItem(@RequestParam int mapIdx, PostItemReq postItemReq){
//        GetItemDetailRes getItemDetailRes = itemService.createItem(mapIdx, postItemReq);
//        return ResponseEntity.ok(BaseResponse.create(SUCCESS, getItemDetailRes));
//    }


    @ApiOperation(value = "???????????? ?????? ?????? ??????", notes = "???????????? ?????? ?????? ??????")
    @PostMapping("/upload/{itemIdx}/picture")
    public List<String> itemPictureUpload(
            @RequestPart("images") List<MultipartFile> multipartFiles, @PathVariable int itemIdx) throws IOException {
            List<String> list = new ArrayList<>();
            for (MultipartFile file : multipartFiles) {
                String fileUrl = fileService.itemPictureUpload(itemIdx, file, "careerup-bucket", "images");
                list.add(fileUrl);
            }
            return list;
    }

    @ApiOperation(value = "???????????? ?????? ??????", notes = "???????????? ?????? ??????")
    @PostMapping("upload/{itemIdx}/files")
    public List<String> itemFileUpload(
            @RequestPart("files") List<MultipartFile> multipartFiles, @PathVariable int itemIdx) throws IOException {
        List<String> list = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            String fileUrl = fileService.itemPictureUpload(itemIdx, file, "careerup-bucket", "files");
            list.add(fileUrl);
        }
        return list;
    }
}
