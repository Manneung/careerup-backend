package com.manneung.careerup.domain.file.repository;


import com.manneung.careerup.domain.file.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
    List<File> findFilesByItemIdx(int itemIdx);

}
