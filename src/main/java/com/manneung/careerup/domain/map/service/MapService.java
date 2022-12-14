package com.manneung.careerup.domain.map.service;


import com.manneung.careerup.domain.item.model.Item;
import com.manneung.careerup.domain.item.model.PostItemReq;
import com.manneung.careerup.domain.item.repository.ItemRepository;
import com.manneung.careerup.domain.item.service.ItemService;
import com.manneung.careerup.domain.map.model.Map;
import com.manneung.careerup.domain.map.model.dto.GetMapRes;
import com.manneung.careerup.domain.map.model.dto.PostMapReq;
import com.manneung.careerup.domain.map.model.dto.PostMapRes;
import com.manneung.careerup.domain.map.repository.MapRepository;
import com.manneung.careerup.domain.user.model.User;
import com.manneung.careerup.domain.user.repository.UserRepository;
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


    public PostMapRes createMap(PostMapReq postMapReq) { //닉네임, 타이틀, 직업군, 아이템리스트
        PostMapRes postMapRes = new PostMapRes();
        Map newMap = new Map(); //DB에 저장할 객체


        //커리어맵 정보 저장
        User findUser = userRepository.findUserByNickname(postMapReq.getNickname());

        if(findUser != null){
            newMap.setUserIdx(userRepository.findUserByNickname(postMapReq.getNickname()).getUserIdx());
            newMap.setNickname(postMapReq.getNickname());
            newMap.setTitle(postMapReq.getTitle());
            newMap.setJob(postMapReq.getJob());
            mapRepository.save(newMap);

            //아이템 정보 저장
            List<PostItemReq> items = postMapReq.getItemList();
            boolean itemSaved = itemService.saveItems(newMap.getMapIdx(), items);

            if(itemSaved){
                postMapRes.setMapIdx(newMap.getMapIdx());
                return postMapRes;
            }
        }


        return null;
    }


    public List<GetMapRes> searchMapsByNickname(String nickname){
        List<GetMapRes> findMaps = new ArrayList<>();
        Integer userIdx;

        //유저 찾기
        if(userRepository.findUserByNickname(nickname) == null){
            return null;
        } else {
            userIdx = userRepository.findUserByNickname(nickname).getUserIdx();
        }

        //해당하는 커리어맵 리스트
        List<Map> maps = mapRepository.findAllByUserIdx(userIdx);

        for(Map m : maps){
            GetMapRes getMapRes = new GetMapRes(m.getMapIdx(), m.getTitle());
            findMaps.add(getMapRes);
        }

        //커리어맵 리스트가 존재하는지에 따라 분리
        if(!findMaps.isEmpty()){
            return findMaps;
        } else {
            return null;
        }
    }


    //맵 제목으로 맵 디테일 정보 찾기
//    public GetMapDetailRes searchMapDetail(String title){
//
//    }
}
