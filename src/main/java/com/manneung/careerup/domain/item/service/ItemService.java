package com.manneung.careerup.domain.item.service;


import com.manneung.careerup.domain.item.model.Item;
import com.manneung.careerup.domain.item.model.PostItemReq;
import com.manneung.careerup.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    public boolean saveItems(int mapIdx, List<PostItemReq> items) {
        //순서가 바뀐 상태의 item리스트를 받아옴
        for(PostItemReq postItemReq : items) {
            Item toSave = postItemReq.toEntity(mapIdx, postItemReq);
            itemRepository.save(toSave);
        }
        return true;
    }


    public List<Item> searchItemListByMapIdx(int mapIdx){
        return itemRepository.findAllByMapIdxOrderBySequenceAsc(mapIdx);
    }
}
