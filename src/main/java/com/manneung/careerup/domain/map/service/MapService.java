package com.manneung.careerup.domain.map.service;


import com.manneung.careerup.domain.item.model.Item;
import com.manneung.careerup.domain.item.repository.ItemRepository;
import com.manneung.careerup.domain.item.service.ItemService;
import com.manneung.careerup.domain.map.model.Map;
import com.manneung.careerup.domain.map.model.dto.PostMapReq;
import com.manneung.careerup.domain.map.model.dto.PostMapRes;
import com.manneung.careerup.domain.map.repository.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MapService { //map, item

    private final MapRepository mapRepository;
    private final ItemService itemService;


    public PostMapRes createMap(PostMapReq postMapReq) {
        PostMapRes postMapRes = new PostMapRes();
        Map newMap = new Map(); //DB에 저장할 객체


        //커리어맵 정보 저장
        newMap.setTitle(postMapReq.getTitle());
        newMap.setUserIdx(postMapReq.getUserIdx());
        newMap.setJob(postMapReq.getJob());
        mapRepository.save(newMap);

        //커리어맵 내 아이템 정보 저장
        List<Item> items = postMapReq.getItemList();
        boolean itemSaved = itemService.saveItems(items);

        if(itemSaved){
            postMapRes.setMapIdx(newMap.getMapIdx());
            return postMapRes;
        }

        return null;
    }
}
