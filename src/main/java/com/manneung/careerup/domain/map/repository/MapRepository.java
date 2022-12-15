package com.manneung.careerup.domain.map.repository;

import com.manneung.careerup.domain.map.model.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MapRepository extends JpaRepository<Map, Integer> {

    List<Map> findAllByUserIdx(Integer userIdx);

    Map findByMapIdx(int mapIdx);
}
