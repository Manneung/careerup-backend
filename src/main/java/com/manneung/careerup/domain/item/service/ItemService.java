package com.manneung.careerup.domain.item.service;


import com.manneung.careerup.domain.file.model.File;
import com.manneung.careerup.domain.file.model.GetFile;
import com.manneung.careerup.domain.file.service.FileService;
import com.manneung.careerup.domain.item.model.Item;
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
import com.manneung.careerup.domain.item.repository.ItemRepository;
import com.manneung.careerup.domain.map.model.Map;
import com.manneung.careerup.domain.map.repository.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final MapRepository mapRepository;
    private final FileService fileService;




    public boolean existsByItemIdx(int itemIdx){
        return itemRepository.existsByItemIdx(itemIdx);
    }



    /**
     * CREATE
     * */
    public GetCertificateRes createCertificate(int mapIdx, PostCertificateReq postCertificateReq){

        if(!mapRepository.existsMapByMapIdx(mapIdx)) return null;

        Item newItem = new Item();

        //아이템 내용
        newItem.setMapIdx(mapIdx);
        newItem.setTitle(postCertificateReq.getTitle());
        newItem.setSubtitle(postCertificateReq.getSubtitle());
        newItem.setSequence(postCertificateReq.getSequence());
        newItem.setCategory("자격증");
        newItem.setInstitution(postCertificateReq.getInstitution());
        newItem.setPeriod(postCertificateReq.getPeriod());
        newItem.setAcquisition(postCertificateReq.getAcquisition());
        newItem.setContent(postCertificateReq.getContent());
        newItem.setRealization(postCertificateReq.getRealization());

        itemRepository.save(newItem);

        return GetCertificateRes.from(newItem);
    }
    public GetClubRes createClub(int mapIdx, PostClubReq  postClubReq){
        if(!mapRepository.existsMapByMapIdx(mapIdx)) return null;

        Item newItem = new Item();

        //아이템 내용
        newItem.setMapIdx(mapIdx);
        newItem.setTitle(postClubReq.getTitle());
        newItem.setSubtitle(postClubReq.getSubtitle());
        newItem.setSequence(postClubReq.getSequence());
        newItem.setCategory("동아리");
        newItem.setPeriod(postClubReq.getPeriod());
        newItem.setRole(postClubReq.getRole());
        newItem.setContent(postClubReq.getContent());
        newItem.setRealization(postClubReq.getRealization());

        itemRepository.save(newItem);

        return GetClubRes.from(newItem);
    }
    public GetContestRes createContest(int mapIdx, PostContestReq postContestReq){
        if(!mapRepository.existsMapByMapIdx(mapIdx)) return null;

        Item newItem = new Item();

        //아이템 내용
        newItem.setMapIdx(mapIdx);
        newItem.setTitle(postContestReq.getTitle());
        newItem.setSubtitle(postContestReq.getSubtitle());
        newItem.setSequence(postContestReq.getSequence());
        newItem.setCategory("공모전");
        newItem.setInstitution(postContestReq.getInstitution());
        newItem.setField(postContestReq.getField());
        newItem.setPeriod(postContestReq.getPeriod());
        newItem.setContent(postContestReq.getContent());
        newItem.setRealization(postContestReq.getRealization());

        itemRepository.save(newItem);

        return GetContestRes.from(newItem);
    }
    public GetEtcRes createEtc(int mapIdx, PostEtcReq postEtcReq){

        if(!mapRepository.existsMapByMapIdx(mapIdx)) return null;

        Item newItem = new Item();

        //아이템 내용
        newItem.setMapIdx(mapIdx);
        newItem.setSequence(postEtcReq.getSequence());
        newItem.setTitle(postEtcReq.getTitle());
        newItem.setSubtitle(postEtcReq.getSubtitle());
        newItem.setCategory("기타");
        newItem.setPeriod(postEtcReq.getPeriod());
        newItem.setRole(postEtcReq.getRole());
        newItem.setContent(postEtcReq.getContent());
        newItem.setRealization(postEtcReq.getRealization());

        itemRepository.save(newItem);

        return GetEtcRes.from(newItem);
    }
    public GetExternalActivityRes createExternalActivity(int mapIdx, PostExternalActivityReq postExternalActivityReq){
        if(!mapRepository.existsMapByMapIdx(mapIdx)) return null;

        Item newItem = new Item();

        //아이템 내용
        newItem.setMapIdx(mapIdx);
        newItem.setTitle(postExternalActivityReq.getTitle());
        newItem.setSubtitle(postExternalActivityReq.getSubtitle());
        newItem.setSequence(postExternalActivityReq.getSequence());
        newItem.setCategory("대외활동");
        newItem.setField(postExternalActivityReq.getField());
        newItem.setInstitution(postExternalActivityReq.getInstitution());
        newItem.setPeriod(postExternalActivityReq.getPeriod());
        newItem.setContent(postExternalActivityReq.getContent());
        newItem.setRealization(postExternalActivityReq.getRealization());

        itemRepository.save(newItem);

        return GetExternalActivityRes.from(newItem);
    }
    public GetStudyRes createStudy(int mapIdx, PostStudyReq postStudyReq){

        if(!mapRepository.existsMapByMapIdx(mapIdx)) return null;

        Item newItem = new Item();
        //아이템 내용
        newItem.setMapIdx(mapIdx);
        newItem.setTitle(postStudyReq.getTitle());
        newItem.setSubtitle(postStudyReq.getSubtitle());
        newItem.setSequence(postStudyReq.getSequence());
        newItem.setField(postStudyReq.getField());
        newItem.setCategory("스터디");
        newItem.setPeriod(postStudyReq.getPeriod());
        newItem.setContent(postStudyReq.getContent());
        newItem.setRealization(postStudyReq.getRealization());

        itemRepository.save(newItem);

        return GetStudyRes.from(newItem);
    }



    /**
     * MODIFY
     * */
    public GetCertificateRes modifyCertificate(int itemIdx, PostItemReq postItemReq) {
        Item item = itemRepository.findByItemIdx(itemIdx);

        if(postItemReq.getTitle() != null)
            item.setTitle(postItemReq.getTitle());
        if(postItemReq.getSubtitle() != null)
            item.setSubtitle(postItemReq.getSubtitle());
        if(postItemReq.getInstitution() != null)
            item.setInstitution(postItemReq.getInstitution());
        if(postItemReq.getPeriod() != null)
            item.setPeriod(postItemReq.getPeriod());
        if(postItemReq.getAcquisition() != null)
            item.setAcquisition(postItemReq.getAcquisition());
        if(postItemReq.getContent() != null)
            item.setContent(postItemReq.getContent());
        if(postItemReq.getRealization() != null)
            item.setRealization(postItemReq.getRealization());

        itemRepository.save(item);
        return GetCertificateRes.from(item);
    }

    public GetClubRes modifyClub(int itemIdx,  PostItemReq postItemReq) {
        Item item = itemRepository.findByItemIdx(itemIdx);

        if(postItemReq.getTitle() != null)
            item.setTitle(postItemReq.getTitle());
        if(postItemReq.getSubtitle() != null)
            item.setSubtitle(postItemReq.getSubtitle());
        if(postItemReq.getPeriod() != null)
            item.setPeriod(postItemReq.getPeriod());
        if(postItemReq.getRole() != null)
            item.setRole(postItemReq.getRole());
        if(postItemReq.getContent() != null)
            item.setContent(postItemReq.getContent());
        if(postItemReq.getRealization() != null)
            item.setRealization(postItemReq.getRealization());

        itemRepository.save(item);
        return GetClubRes.from(item);
    }

    public GetContestRes modifyContest(int itemIdx,  PostItemReq postItemReq) {
        Item item = itemRepository.findByItemIdx(itemIdx);

        if(postItemReq.getTitle() != null)
            item.setTitle(postItemReq.getTitle());
        if(postItemReq.getSubtitle() != null)
            item.setSubtitle(postItemReq.getSubtitle());
        if(postItemReq.getInstitution() != null)
            item.setInstitution(postItemReq.getInstitution());
        if(postItemReq.getPeriod() != null)
            item.setPeriod(postItemReq.getPeriod());
        if(postItemReq.getField() != null)
            item.setField(postItemReq.getField());
        if(postItemReq.getContent() != null)
            item.setContent(postItemReq.getContent());
        if(postItemReq.getRealization() != null)
            item.setRealization(postItemReq.getRealization());

        itemRepository.save(item);
        return GetContestRes.from(item);
    }

    public GetEtcRes modifyEtc(int itemIdx,  PostItemReq postItemReq) {
        Item item = itemRepository.findByItemIdx(itemIdx);

        if(postItemReq.getTitle() != null)
            item.setTitle(postItemReq.getTitle());
        if(postItemReq.getSubtitle() != null)
            item.setSubtitle(postItemReq.getSubtitle());
        if(postItemReq.getPeriod() != null)
            item.setPeriod(postItemReq.getPeriod());
        if(postItemReq.getRole() != null)
            item.setRole(postItemReq.getRole());
        if(postItemReq.getContent() != null)
            item.setContent(postItemReq.getContent());
        if(postItemReq.getRealization() != null)
            item.setRealization(postItemReq.getRealization());

        itemRepository.save(item);
        return GetEtcRes.from(item);
    }

    public GetExternalActivityRes modifyExternalActivity(int itemIdx, PostItemReq postItemReq) {
        Item item = itemRepository.findByItemIdx(itemIdx);

        if(postItemReq.getTitle() != null)
            item.setTitle(postItemReq.getTitle());
        if(postItemReq.getSubtitle() != null)
            item.setSubtitle(postItemReq.getSubtitle());
        if(postItemReq.getInstitution() != null)
            item.setInstitution(postItemReq.getInstitution());
        if(postItemReq.getPeriod() != null)
            item.setPeriod(postItemReq.getPeriod());
        if(postItemReq.getField() != null)
            item.setField(postItemReq.getField());
        if(postItemReq.getContent() != null)
            item.setContent(postItemReq.getContent());
        if(postItemReq.getRealization() != null)
            item.setRealization(postItemReq.getRealization());

        itemRepository.save(item);
        return GetExternalActivityRes.from(item);
    }

    public GetStudyRes modifyStudy(int itemIdx, PostItemReq postItemReq) {
        Item item = itemRepository.findByItemIdx(itemIdx);

        if(postItemReq.getTitle() != null)
            item.setTitle(postItemReq.getTitle());
        if(postItemReq.getSubtitle() != null)
            item.setSubtitle(postItemReq.getSubtitle());
        if(postItemReq.getPeriod() != null)
            item.setPeriod(postItemReq.getPeriod());
        if(postItemReq.getField() != null)
            item.setField(postItemReq.getField());
        if(postItemReq.getContent() != null)
            item.setContent(postItemReq.getContent());
        if(postItemReq.getRealization() != null)
            item.setRealization(postItemReq.getRealization());

        itemRepository.save(item);
        return GetStudyRes.from(item);
    }



    /**
     * CHANGE SEQUENCE
     * */
    //맵 내 아이템 순서변경하기
    public List<GetItemRes> changeItemSequence(int mapIdx, List<PatchSequenceReq> sequenceReqList) {
        List<Item> items = itemRepository.findAllByMapIdx(mapIdx);

        for(Item item : items){
            for(PatchSequenceReq patchSequenceReq : sequenceReqList){
                if(item.getItemIdx() == patchSequenceReq.getItemIdx()){
                    item.setSequence(patchSequenceReq.getSequence());
                }
                itemRepository.save(item);
            }
        }

        List<GetItemRes> getItemResList = searchItemListSimple(mapIdx);
        return getItemResList;
    }


    /**
     * DELETE
     * */
    public String deleteItem(int itemIdx) {

        //아이템 삭제
        Item item = itemRepository.findByItemIdx(itemIdx);
        itemRepository.delete(item);

        //아이템 삭제 후 시퀀스 재정렬 로직
        List<Item> itemList = itemRepository.findAllByMapIdxOrderBySequenceAsc(item.getMapIdx());

        int sq = 1;
        for(Item i : itemList){
            i.setSequence(sq);
            itemRepository.save(i);
            sq++;
        }

        //item 존재 여부에 따라 null반환
        if(itemRepository.findByItemIdx(itemIdx) == null){
            return "성공적으로 삭제되었습니다";
        } else {
            return "활동이 삭제되지 않았습니다";
        }
    }


    //자세하게 아이템 내용 보여주기
    public GetItemDetailRes showItemDetail(int itemIdx){
        List<GetFile> getFiles = new ArrayList<>();
        List<File> files = fileService.findFilesByItemIdx(itemIdx);
        Item findItem = itemRepository.findByItemIdx(itemIdx);


        for(File f : files){
            GetFile getFile = new GetFile(f.getFileIdx(),f.getFileTitle(), f.getFileType(), f.getFileUrl());
            getFiles.add(getFile);
        }

        if(findItem != null){
            return GetItemDetailRes.from(findItem, getFiles);
        } else {
            return null;
        }
    }


    //아이템 리스트 조회(item테이블에 있는 map_idx를 이용해서 찾는 방법
    public List<Item> searchItemListByMapIdx(int mapIdx){
        return itemRepository.findAllByMapIdxOrderBySequenceAsc(mapIdx);
    }



    //간략하게 아이템 리스트 보여주기(커리어맵에서 옆에 제목으로만 보이는 것)
    public List<GetItemRes> searchItemListSimple(int mapIdx){
        List<Item> findItemList = searchItemListByMapIdx(mapIdx);
        List<GetItemRes> getItemResList = new ArrayList<>();

        for(Item i : findItemList){
            GetItemRes getItemRes = new GetItemRes(i.getItemIdx(), i.getTitle(), i.getCategory(), i.getSequence());
            getItemResList.add(getItemRes);
        }

        return getItemResList;
    }

}