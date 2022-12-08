package com.manneung.careerup.domain.map.controller;


import com.manneung.careerup.domain.map.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/map")
public class MapController {
    private final MapService mapService;




}
