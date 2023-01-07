package com.manneung.careerup.domain.file.service;


import com.manneung.careerup.domain.file.model.File;
import com.manneung.careerup.domain.file.repository.FileRepository;
import com.manneung.careerup.global.s3.S3UploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;

    private final S3UploaderService s3UploaderService;


    //각 활동에 들어간 파일 목록 전체 불러오기(사진, 파일 구분x)
    public List<File> findFilesByItemIdx(int itemIdx){
        return fileRepository.findFilesByItemIdx(itemIdx);
    }

    //파일 업로드
    public String upload(MultipartFile multipartFile, String bucket, String dirName ) throws IOException {
        return s3UploaderService.upload(multipartFile, "careerup-bucket", dirName);
    }




    //fileType: "활동사진", "활동파일"
    //활동 사진 업로드
    public String itemPictureUpload(int itemIdx, MultipartFile multipartFile, String bucket, String dirName ) throws IOException {
        String fileName = s3UploaderService.upload(multipartFile, "careerup-bucket", dirName);

        File newFile = new File();
        newFile.setItemIdx(itemIdx);
        if (dirName == "files") newFile.setFileType("활동파일");
        else newFile.setFileType("활동사진");
        newFile.setFileName(fileName);

        fileRepository.save(newFile);

        return fileName;
    }

    public Boolean deleteFile(int fileIdx) {
        try {
            fileRepository.deleteById(fileIdx);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public File updateFile(int fileIdx, File file) {
        // Get the file to be updated
        Optional<File> optionalFile = fileRepository.findById(fileIdx);
        if (!optionalFile.isPresent()) {
            // Return null if the file with the given fileIdx does not exist
            return null;
        }
        File fileToUpdate = optionalFile.get();
        // Update the file according to your business logic
        fileToUpdate.setFileType(file.getFileType());
        fileToUpdate.setFileName(file.getFileName());
        // Save the updated file to the database
        return fileRepository.save(fileToUpdate);
    }
}
