package com.manneung.careerup.domain.map.service;


import com.manneung.careerup.domain.item.model.Item;
import com.manneung.careerup.domain.item.model.dto.item.GetItemRes;
import com.manneung.careerup.domain.item.service.ItemService;
import com.manneung.careerup.domain.map.model.Map;
import com.manneung.careerup.domain.map.model.dto.*;
import com.manneung.careerup.domain.map.repository.MapRepository;
import com.manneung.careerup.domain.user.model.User;
import com.manneung.careerup.domain.user.repository.UserRepository;
import com.manneung.careerup.global.jwt.SecurityUtil;
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


    //CRUD
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





    //조회 메소드

    public List<GetMapSimpleRes> searchMyMaps() {
        List<GetMapSimpleRes> findMaps = new ArrayList<>();
        User user = findNowLoginUser();

        List<Map> maps = mapRepository.findAllByUserIdx(user.getUserIdx());

        if(maps.isEmpty()) return null;

        for(Map m: maps){
            if(m.getStatus().equals("A")){  //활성 상태만
                GetMapSimpleRes getMapSimpleRes = new GetMapSimpleRes(m.getMapIdx(), m.getTitle(), m.getCareer());
                findMaps.add(getMapSimpleRes);
            }
        }

        return findMaps;
    }



    ////////////////////////////////////////세부정보 조회////////////////////////////////////////////////
    //맵 인덱스으로 맵 디테일 정보 찾기

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



//    public List<GetMapSimpleRes> searchMapsByNickname(String nickname){
//        List<GetMapSimpleRes> findMaps = new ArrayList<>();
//        Integer userIdx;
//
//        //유저 찾기
//        if(userRepository.findUserByNickname(nickname) == null){
//            return null;
//        } else {
//            userIdx = userRepository.findUserByNickname(nickname).getUserIdx();
//        }
//
//        List<Map> findMapList = mapRepository.findAllByUserIdx(userIdx);
//        for(Map m: findMapList){
//            if(m.getStatus().equals("A")){
//                GetMapSimpleRes getMapSimpleRes = new GetMapSimpleRes(m.getMapIdx(), m.getTitle());
//                findMaps.add(getMapSimpleRes);
//            }
//        }
//
//        //커리어맵 리스트가 존재하는지에 따라 분리
//        if(!findMaps.isEmpty()){
//            return findMaps;
//        } else {
//            return null;
//        }
//    }
//
//    public List<GetMapSimpleRes> searchMapsByTitle(String title) {
//        List<GetMapSimpleRes> findMaps = new ArrayList<>();
//        List<Map> findMapList = mapRepository.findAllByTitleContaining(title);
//
//        if(findMapList != null){
//            for(Map m : findMapList) {
//                if(m.getStatus().equals("A")){
//                    GetMapSimpleRes getMapSimpleRes = new GetMapSimpleRes(m.getMapIdx(), m.getTitle());
//                    findMaps.add(getMapSimpleRes);
//                }
//            }
//            return findMaps;
//        }
//        return null;
//    }





}
