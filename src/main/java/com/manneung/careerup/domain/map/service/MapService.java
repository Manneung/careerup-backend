package com.manneung.careerup.domain.map.service;


import com.manneung.careerup.domain.map.repository.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MapService { //map, activity

    private final MapRepository mapRepository;
}
