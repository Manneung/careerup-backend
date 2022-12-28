package com.manneung.careerup.domain.file.controller;


import com.manneung.careerup.domain.file.service.FileService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RequiredArgsConstructor
@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;


    @ApiOperation(value = "파일 한 개 업로드 테스트", notes = "파일 한 개 업로드 테스트")
    @PostMapping("/upload")
    public String imageUpload(@RequestPart("data") MultipartFile multipartFile) throws IOException {
        return fileService.upload(multipartFile, "careerup-bucket", "image");
    }

    @ApiOperation(value = "아이템에 활동 사진 추가", notes = "아이템에 활동 사진 추가")
    @PostMapping("/upload/{itemIdx}/picture")
    public String itemPictureUpload(
            @RequestPart("data") MultipartFile multipartFile,
            @PathVariable int itemIdx) throws IOException {

        return fileService.itemPictureUpload(itemIdx, multipartFile, "careerup-bucket", "image" );
    }

}
