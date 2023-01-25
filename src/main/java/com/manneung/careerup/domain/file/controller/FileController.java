package com.manneung.careerup.domain.file.controller;


import com.manneung.careerup.domain.base.BaseResponse;
import com.manneung.careerup.domain.file.model.File;
import com.manneung.careerup.domain.file.service.FileService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.manneung.careerup.domain.base.BaseResponseStatus.SUCCESS;


//@CrossOrigin("https://careerup.netlify.app")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    @ApiOperation(value = "파일 한 개 업로드 테스트(사용x)", notes = "파일 한 개 업로드 테스트")
    @PostMapping("/upload")
    public String imageUpload(@RequestPart("data") MultipartFile multipartFile) throws IOException {
        return fileService.upload(multipartFile, "careerup-bucket", "image");
    }

    @ApiOperation(value = "itemIdx로 파일 불러오기", notes = "itemIdx로 파일 불러오기")
    @GetMapping("/{itemIdx}")
    public ResponseEntity<BaseResponse<List<File>>> getFile(@PathVariable(name = "itemIdx") int itemIdx) {
        return ResponseEntity.ok(BaseResponse.ok(SUCCESS, fileService.findFilesByItemIdx(itemIdx)));
    }

    @ApiOperation(value = "파일idx로 파일 이름,타입 수정", notes = "파일idx로 조회 -> 파일 이름,타입 수정")
    @PatchMapping("/{fileIdx}")
    public ResponseEntity<BaseResponse<File>> modifyFile(@PathVariable(name = "fileIdx") int fileIdx, File file) {
        return ResponseEntity.ok(BaseResponse.ok(SUCCESS, fileService.updateFile(fileIdx, file)));
    }

    @ApiOperation(value = "fileIdx로 파일 삭제", notes = "fileIdx로 조회 -> 파일 삭제")
    @DeleteMapping("/{fileIdx}")
    public ResponseEntity<BaseResponse<Boolean>> deleteFile(@PathVariable(name = "fileIdx") int fileIdx) {
        return ResponseEntity.ok(BaseResponse.ok(SUCCESS, fileService.deleteFile(fileIdx)));
    }
}
