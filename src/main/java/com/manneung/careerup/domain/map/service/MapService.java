package com.manneung.careerup.domain.map.service;


import com.manneung.careerup.domain.item.model.Item;
import com.manneung.careerup.domain.item.model.dto.item.GetItemRes;
import com.manneung.careerup.domain.item.service.ItemService;
import com.manneung.careerup.domain.map.model.Map;
import com.manneung.careerup.domain.map.model.dto.*;
import com.manneung.careerup.domain.map.repository.MapRepository;
import com.manneung.careerup.domain.user.model.User;
import com.manneung.careerup.domain.user.repository.UserRepository;
import com.manneung.careerup.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MapService { //map, item

    private final UserRepository userRepository;
    private final MapRepository mapRepository;
    private final ItemService itemService;


    public User findNowLoginUser(){
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername).orElse(null);
    }


    public boolean existsMapByMapIdx(int mapIdx){
        return mapRepository.existsMapByMapIdx(mapIdx);
    }


    public PostMapRes createMap(PostMapReq postMapReq) { //맵 자체만 생성

        Map newMap = new Map(); //DB에 저장할 객체
        User findUser = findNowLoginUser();

        if(findUser != null){

            //새로운 맵의 정보만 저장
            newMap.setUserIdx(findUser.getUserIdx());
            newMap.setTitle(postMapReq.getTitle());
            newMap.setCareer(postMapReq.getCareer());
            mapRepository.save(newMap);

            return new PostMapRes(newMap.getMapIdx());
        }
        return null;
    }


    public PatchMapRes modifyMap(int mapIdx, PatchMapReq patchMapReq) {
        Map findMap = mapRepository.findByMapIdx(mapIdx);

        findMap.setTitle(patchMapReq.getTitle());
        findMap.setCareer(patchMapReq.getCareer());
        mapRepository.save(findMap);

        PatchMapRes patchMapRes = new PatchMapRes(findMap.getMapIdx());

        return patchMapRes;
    }

    public DeleteMapRes deleteMap(int mapIdx) {
        Map findMap = mapRepository.findByMapIdx(mapIdx);

        if(findMap != null){
            if(findMap.getStatus().equals("A")){
                //맵 상제 상태로 변경
                findMap.setStatus("D");
                mapRepository.save(findMap);

                //맵 내의 아이템 전부 삭제
                List<Item> itemList = itemService.searchItemListByMapIdx(findMap.getMapIdx());
                for(Item i: itemList){
                    itemService.deleteItem(i.getItemIdx());
                }

                //삭제된 맵 인덱스 반환
                return new DeleteMapRes(mapIdx);
            }
            else{
                return null;
            }
        }
        return null;
    }



    public List<GetMapSimpleRes> searchMyMaps() {
        List<GetMapSimpleRes> findMaps = new ArrayList<>();
        User user = findNowLoginUser();

        List<Map> maps = mapRepository.findAllByUserIdx(user.getUserIdx());

        if(maps.isEmpty()) return findMaps;

        for(Map m: maps){
            if(m.getStatus().equals("A")){  //활성 상태만
                GetMapSimpleRes getMapSimpleRes = new GetMapSimpleRes(m.getMapIdx(), m.getTitle(), m.getCareer());
                findMaps.add(getMapSimpleRes);
            }
        }

        return findMaps;
    }


    public GetMapDetailRes searchMapDetail(int mapIdx){
        GetMapDetailRes getMapDetailRes = new GetMapDetailRes(); //mapidx, 닉네임, 아이템리스트

        if(mapRepository.findByMapIdx(mapIdx).getStatus().equals("A")){
            Map findMap = mapRepository.findByMapIdx(mapIdx);

            User user = findNowLoginUser();
            List<GetItemRes> getItemResList = itemService.searchItemListSimple(mapIdx);

            getMapDetailRes.setMapIdx(findMap.getMapIdx());
            getMapDetailRes.setName(user.getName());
            getMapDetailRes.setItemList(getItemResList);

            return getMapDetailRes;
        } else{
            return null;
        }
    }

}