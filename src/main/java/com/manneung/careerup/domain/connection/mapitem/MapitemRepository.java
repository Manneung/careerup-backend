package com.manneung.careerup.domain.connection.mapitem;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface MapitemRepository extends JpaRepository<Mapitem, Integer> {

    List<Mapitem> findAllByMapIdx(int mapIdx); //한 커리어맵이 갖고 있는 아이템 리스트의 idx
    Mapitem findAllByItemIdx(int itemIdx);

}
