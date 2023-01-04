package com.manneung.careerup.domain.item.service;



import com.manneung.careerup.domain.file.service.FileService;
import com.manneung.careerup.domain.item.model.Item;
import com.manneung.careerup.domain.item.model.dto.study.PostStudyReq;
import com.manneung.careerup.domain.item.model.dto.certifcate.PostCertificateReq;
import com.manneung.careerup.domain.item.model.dto.club.GetClubRes;
import com.manneung.careerup.domain.item.model.dto.study.GetStudyRes;
import com.manneung.careerup.domain.item.model.dto.certifcate.GetCertificateRes;
import com.manneung.careerup.domain.item.model.dto.club.PostClubReq;
import com.manneung.careerup.domain.item.model.dto.contest.GetContestRes;
import com.manneung.careerup.domain.item.model.dto.contest.PostContestReq;
import com.manneung.careerup.domain.item.model.dto.etc.GetEtcRes;
import com.manneung.careerup.domain.item.model.dto.etc.PostEtcReq;
import com.manneung.careerup.domain.item.model.dto.external_activitiy.GetExternalActivityRes;
import com.manneung.careerup.domain.item.model.dto.external_activitiy.PostExternalActivityReq;
import com.manneung.careerup.domain.item.model.dto.item.GetItemDetailRes;
import com.manneung.careerup.domain.item.model.dto.item.GetItemRes;
import com.manneung.careerup.domain.item.model.dto.item.PostItemReq;
import com.manneung.careerup.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    private final FileService fileService;


    public GetItemDetailRes createItem(int mapIdx, PostItemReq postItemReq){
        Item newItem = new Item();

        //아이템 내용
        newItem.setMapIdx(mapIdx);
        newItem.setSequence(postItemReq.getSequence());
        newItem.setCategory(postItemReq.getCategory());
        newItem.setInstitution(postItemReq.getInstitution());
        newItem.setPeriod(postItemReq.getPeriod());
        newItem.setAcquisition(postItemReq.getAcquisition());
        newItem.setField(postItemReq.getField());
        newItem.setRole(postItemReq.getRole());
        newItem.setContent(postItemReq.getContent());
        newItem.setRealization(postItemReq.getRealization());

        itemRepository.save(newItem);

        return GetItemDetailRes.from(newItem);
    }

    public GetCertificateRes createCertificate(int mapIdx, PostCertificateReq postCertificateReq){
        Item newItem = new Item();

        //아이템 내용
        newItem.setMapIdx(mapIdx);
        newItem.setTitle(postCertificateReq.getTitle());
        newItem.setSequence(postCertificateReq.getSequence());
        newItem.setCategory(postCertificateReq.getCategory());
        newItem.setInstitution(postCertificateReq.getInstitution());
        newItem.setPeriod(postCertificateReq.getPeriod());
        newItem.setAcquisition(postCertificateReq.getAcquisition());
        newItem.setContent(postCertificateReq.getContent());
        newItem.setRealization(postCertificateReq.getRealization());

        itemRepository.save(newItem);

        return GetCertificateRes.from(newItem);
    }
    public GetClubRes createClub(int mapIdx, PostClubReq  postClubReq){
        Item newItem = new Item();

        //아이템 내용
        newItem.setMapIdx(mapIdx);
        newItem.setTitle(postClubReq.getTitle());
        newItem.setSequence(postClubReq.getSequence());
        newItem.setCategory(postClubReq.getCategory());
        newItem.setPeriod(postClubReq.getPeriod());
        newItem.setRole(postClubReq.getRole());
        newItem.setContent(postClubReq.getContent());
        newItem.setRealization(postClubReq.getRealization());

        itemRepository.save(newItem);

        return GetClubRes.from(newItem);
    }
    public GetContestRes createContest(int mapIdx, PostContestReq postContestReq){
        Item newItem = new Item();

        //아이템 내용
        newItem.setMapIdx(mapIdx);
        newItem.setTitle(postContestReq.getTitle());
        newItem.setSequence(postContestReq.getSequence());
        newItem.setCategory(postContestReq.getCategory());
        newItem.setInstitution(postContestReq.getInstitution());
        newItem.setField(postContestReq.getField());
        newItem.setPeriod(postContestReq.getPeriod());
        newItem.setContent(postContestReq.getContent());
        newItem.setRealization(postContestReq.getRealization());

        itemRepository.save(newItem);

        return GetContestRes.from(newItem);
    }
    public GetEtcRes createEtc(int mapIdx, PostEtcReq postEtcReq){
        Item newItem = new Item();

        //아이템 내용
        newItem.setMapIdx(mapIdx);
        newItem.setSequence(postEtcReq.getSequence());
        newItem.setCategory(postEtcReq.getCategory());
        newItem.setPeriod(postEtcReq.getPeriod());
        newItem.setRole(postEtcReq.getRole());
        newItem.setContent(postEtcReq.getContent());
        newItem.setRealization(postEtcReq.getRealization());

        itemRepository.save(newItem);

        return GetEtcRes.from(newItem);
    }
    public GetExternalActivityRes createExternalActivity(int mapIdx, PostExternalActivityReq postExternalActivityReq){
        Item newItem = new Item();

        //아이템 내용
        newItem.setMapIdx(mapIdx);
        newItem.setTitle(postExternalActivityReq.getTitle());
        newItem.setSequence(postExternalActivityReq.getSequence());
        newItem.setCategory(postExternalActivityReq.getCategory());
        newItem.setInstitution(postExternalActivityReq.getInstitution());
        newItem.setPeriod(postExternalActivityReq.getPeriod());
        newItem.setContent(postExternalActivityReq.getContent());
        newItem.setRealization(postExternalActivityReq.getRealization());

        itemRepository.save(newItem);

        return GetExternalActivityRes.from(newItem);
    }
    public GetStudyRes createStudy(int mapIdx, PostStudyReq postStudyReq){
        Item newItem = new Item();

        //아이템 내용
        newItem.setMapIdx(mapIdx);
        newItem.setTitle(postStudyReq.getTitle());
        newItem.setSequence(postStudyReq.getSequence());
        newItem.setCategory(postStudyReq.getCategory());
        newItem.setPeriod(postStudyReq.getPeriod());
        newItem.setContent(postStudyReq.getContent());
        newItem.setRealization(postStudyReq.getRealization());

        itemRepository.save(newItem);

        return GetStudyRes.from(newItem);
    }

    //자세하게 아이템 내용 보여주기
    public GetItemDetailRes showItemDetail(int itemIdx){

        Item findItem = itemRepository.findByItemIdx(itemIdx);

        if(findItem != null){
            return GetItemDetailRes.from(findItem);
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
            GetItemRes getItemRes = new GetItemRes(i.getTitle(), i.getSequence());
            getItemResList.add(getItemRes);
        }

        return getItemResList;
    }


    //자격증 내용 보여주기
    public GetCertificateRes showCertificate(int itemIdx) {
        Item findItem = itemRepository.findByItemIdx(itemIdx);

        if (findItem != null) {
            return GetCertificateRes.from(findItem);
        } else {
            return null;
        }
    }
    //공모전 내용 보여주기
    public GetContestRes showContest(int itemIdx) {
        Item findItem = itemRepository.findByItemIdx(itemIdx);

        if (findItem != null) {
            return GetContestRes.from(findItem);
        } else {
            return null;
        }
    }
    //기타 내용 보여주기
    public GetEtcRes showEtc(int itemIdx) {
        Item findItem = itemRepository.findByItemIdx(itemIdx);

        if (findItem != null) {
            return GetEtcRes.from(findItem);
        } else {
            return null;
        }
    }
    //대외활동 내용 보여주기
    public GetExternalActivityRes showExternalActivity(int itemIdx) {
        Item findItem = itemRepository.findByItemIdx(itemIdx);

        if (findItem != null) {
            return GetExternalActivityRes.from(findItem);
        } else {
            return null;
        }
    }
    //동아리 내용 보여주기
    public GetClubRes showClub(int itemIdx) {
        Item findItem = itemRepository.findByItemIdx(itemIdx);

        if (findItem != null) {
            return GetClubRes.from(findItem);
        } else {
            return null;
        }
    }
    //스터디 내용 보여주기
    public GetStudyRes showStudy(int itemIdx) {
        Item findItem = itemRepository.findByItemIdx(itemIdx);

        if (findItem != null) {
            return GetStudyRes.from(findItem);
        } else {
            return null;
        }
    }



    //    public GetItemDetailRes showItemDetail(int itemIdx){
//        Item findItem = itemRepository.findByItemIdx(itemIdx);
//
//        if(findItem != null){
//            GetItemDetailRes getItemDetailRes = new GetItemDetailRes(findItem.getMapIdx(), findItem.getTitle(), findItem.getCategory());
//            return getItemDetailRes;
//
//        } else {
//            return null;
//        }
//    }



}
