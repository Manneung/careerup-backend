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

    @ApiOperation(value = "파일 한 개 업로드 테스트(사용x)", notes = "파일 한 개 업로드 테스트")
    @PostMapping("/upload")
    public String imageUpload(@RequestPart("data") MultipartFile multipartFile) throws IOException {
        return fileService.upload(multipartFile, "careerup-bucket", "image");
    }

}
