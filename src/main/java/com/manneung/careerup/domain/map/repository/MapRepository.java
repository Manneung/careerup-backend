package com.manneung.careerup.domain.map.repository;

import com.manneung.careerup.domain.map.model.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MapRepository extends JpaRepository<Map, Integer> {

    List<Map> findAllByUserIdx(Integer userIdx);



    List<Map> findAllByTitleContaining(String title);
    //List<Map> findAllByJobContaining(String job);
    Map findByMapIdx(int mapIdx);



}
