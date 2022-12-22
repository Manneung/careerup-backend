package com.manneung.careerup.domain.item.service;



import com.manneung.careerup.domain.item.model.Item;
import com.manneung.careerup.domain.item.model.dto.GetItemDetailRes;
import com.manneung.careerup.domain.item.model.dto.GetItemRes;
import com.manneung.careerup.domain.item.model.dto.PostItemReq;
import com.manneung.careerup.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
//    private final UsermapRepository usermapRepository;
//
//    private final MapitemRepository mapitemRepository;



    //아이템을 생성
    public boolean createItems(int mapIdx, List<PostItemReq> items) {
        //순서가 바뀐 상태의 item리스트를 받아옴
        for(PostItemReq postItemReq : items) {
            Item toSave = postItemReq.toEntity(mapIdx, postItemReq);
            itemRepository.save(toSave);

//            Mapitem mapitem = new Mapitem(mapIdx, toSave.getItemIdx()); //커넥션 저장
//            mapitemRepository.save(mapitem);
        }
        return true;
    }



    //아이템 리스트 조회(item테이블에 있는 map_idx를 이용해서 찾는 방법
    public List<Item> searchItemListByMapIdx(int mapIdx){
        return itemRepository.findAllByMapIdxOrderBySequenceAsc(mapIdx);
    }



    //간략하게 아이템 리스트 보여주기
    public List<GetItemRes> searchItemListSimple(int mapIdx){
        List<Item> findItemList = searchItemListByMapIdx(mapIdx);
        List<GetItemRes> getItemResList = new ArrayList<>();

        for(Item i : findItemList){
            GetItemRes getItemRes = new GetItemRes(i.getTitle(), i.getSequence());
            getItemResList.add(getItemRes);
        }

        return getItemResList;
    }



    //자세하게 아이템 내용 보여주기
    public GetItemDetailRes showItemDetail(int itemIdx){
//        Mapitem connection = mapitemRepository.findAllByItemIdx(itemIdx);
        Item findItem = itemRepository.findByItemIdx(itemIdx);


        if(findItem != null){
            GetItemDetailRes getItemDetailRes =
                    new GetItemDetailRes(findItem.getMapIdx(), findItem.getTitle(), findItem.getCategory());
            return getItemDetailRes;
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
