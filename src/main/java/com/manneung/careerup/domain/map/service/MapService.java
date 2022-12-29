package com.manneung.careerup.domain.map.service;


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


        //해당 유저 누군지 찾기
        User findUser = findNowLoginUser();

        if(findUser != null){

            //새로운 맵의 정보 저장
            newMap.setUserIdx(findUser.getUserIdx());
            newMap.setTitle(postMapReq.getTitle());
            newMap.setCareer(postMapReq.getCareer());
            mapRepository.save(newMap);

            return new PostMapRes(newMap.getMapIdx());

//
//            //아이템 정보 저장
//            List<PostItemReq> items = postMapReq.getItemList();
//            boolean itemSaved = itemService.createItems(newMap.getMapIdx(), items);
//
//            if(itemSaved){
//                postMapRes.setMapIdx(newMap.getMapIdx());
//                return postMapRes;
//            }
        }
        return null;
    }


    public PatchMapRes modifyMap(int mapIdx, PatchMapReq patchMapReq) {
        Map findMap = mapRepository.findByMapIdx(mapIdx);

        findMap.setTitle(patchMapReq.getTitle());
        mapRepository.save(findMap);

        PatchMapRes patchMapRes = new PatchMapRes(findMap.getMapIdx());

        return patchMapRes;
    }

    public DeleteMapRes deleteMap(int mapIdx) {
        Map findMap = mapRepository.findByMapIdx(mapIdx);

        if(findMap != null){
            if(findMap.getStatus().equals("A")){
                findMap.setStatus("D");
                mapRepository.save(findMap);
                return new DeleteMapRes(mapIdx);
            }
            else{
                return null;
            }
        }
        return null;
    }





    //조회 메소드
    public List<GetMapRes> searchMapsByNickname(String nickname){
        List<GetMapRes> findMaps = new ArrayList<>();
        Integer userIdx;

        //유저 찾기
        if(userRepository.findUserByNickname(nickname) == null){
            return null;
        } else {
            userIdx = userRepository.findUserByNickname(nickname).getUserIdx();
        }

        List<Map> findMapList = mapRepository.findAllByUserIdx(userIdx);
        for(Map m: findMapList){
            if(m.getStatus().equals("A")){
                GetMapRes getMapRes = new GetMapRes(m.getMapIdx(), m.getTitle());
                findMaps.add(getMapRes);
            }
        }

        //커리어맵 리스트가 존재하는지에 따라 분리
        if(!findMaps.isEmpty()){
            return findMaps;
        } else {
            return null;
        }
    }

    public List<GetMapRes> searchMapsByTitle(String title) {
        List<GetMapRes> findMaps = new ArrayList<>();
        List<Map> findMapList = mapRepository.findAllByTitleContaining(title);

        if(findMapList != null){
            for(Map m : findMapList) {
                if(m.getStatus().equals("A")){
                    GetMapRes getMapRes = new GetMapRes(m.getMapIdx(), m.getTitle());
                    findMaps.add(getMapRes);
                }
            }
            return findMaps;
        }
        return null;
    }

//    public List<GetMapRes> searchMapsByJob(String job) {
//        List<GetMapRes> findMaps = new ArrayList<>();
//        List<Map> findMapList = mapRepository.findAllByJobContaining(job);
//
//        if(findMapList != null){
//            for(Map m : findMapList) {
//                if(m.getStatus().equals("A")){
//                    GetMapRes getMapRes = new GetMapRes(m.getMapIdx(), m.getTitle());
//                    findMaps.add(getMapRes);
//                }
//            }
//            return findMaps;
//        }
//        return null;
//    }


    ////////////////////////////////////////세부정보 조회////////////////////////////////////////////////
    //맵 제목으로 맵 디테일 정보 찾기

    public GetMapDetailRes searchMapDetail(int mapIdx){
        GetMapDetailRes getMapDetailRes = new GetMapDetailRes(); //mapidx, 닉네임, 아이템리스트

        if(mapRepository.findByMapIdx(mapIdx) != null){
            Map findMap = mapRepository.findByMapIdx(mapIdx);


            //int userIdx  = findMap.getUserIdx();
            //String nickname = findMap.getNickname();
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
