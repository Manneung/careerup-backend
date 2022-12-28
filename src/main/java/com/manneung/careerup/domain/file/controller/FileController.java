package com.manneung.careerup.domain.file.controller;


import com.manneung.careerup.domain.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RequiredArgsConstructor
@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public String imageUpload(@RequestPart("data") MultipartFile multipartFile) throws IOException {
        return fileService.upload(multipartFile, "careerup-bucket", "image");
    }


    @PostMapping("/upload/{itemIdx}/picture")
    public String itemPictureUpload(
            @RequestPart("data") MultipartFile multipartFile,
            @PathVariable int itemIdx) throws IOException {

        return fileService.itemPictureUpload(itemIdx, multipartFile, "careerup-bucket", "image" );
    }

}
