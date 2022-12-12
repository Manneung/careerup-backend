package com.manneung.careerup.domain.item.service;


import com.manneung.careerup.domain.item.model.Item;
import com.manneung.careerup.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    public boolean saveItems(List<Item> items) {
        //순서가 바뀐 상태의 item리스트를 받아옴
        for(Item item : items) {
            //Item newItem = item.toEntity();
            //itemRepository.save(newItem);
        }
        return true;
    }

    public List<Item> searchItemListByMapIdx(int mapIdx){
        return itemRepository.findAllByMapIdxOrderByCountAsc(mapIdx);
    }
}
