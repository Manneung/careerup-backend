package com.manneung.careerup.domain.item.service;



import com.manneung.careerup.domain.file.service.FileService;
import com.manneung.careerup.domain.item.model.Item;
import com.manneung.careerup.domain.item.model.dto.GetItemDetailRes;
import com.manneung.careerup.domain.item.model.dto.GetItemRes;
import com.manneung.careerup.domain.item.model.dto.PostItemReq;
import com.manneung.careerup.domain.item.model.dto.PostItemRes;
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
