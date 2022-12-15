package com.manneung.careerup.domain.connection.usermap;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsermapRepository extends JpaRepository<Usermap, Integer> {

    List<Usermap> findAllByUserIdx(int userIdx); //유저가 갖고 있는 커리어맵 전체의 idx

}
