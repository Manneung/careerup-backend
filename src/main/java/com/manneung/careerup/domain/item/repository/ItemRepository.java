package com.manneung.careerup.domain.item.repository;


import com.manneung.careerup.domain.item.model.Item;
import com.manneung.careerup.domain.map.model.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {



    //mapidx가 일치한 행을 찾고 count를 기준으로 오름차순으로 정렬한 item리스트를 반환
    List<Item> findAllByMapIdxOrderBySequenceAsc(int mapIdx);

    List<Item> findAllByMapIdx(int mapIdx);

    Item findByItemIdx(int itemIdx);

    boolean existsByItemIdx(int itemIdx);

}
