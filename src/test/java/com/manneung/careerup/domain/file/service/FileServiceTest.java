package com.manneung.careerup.domain.file.service;

import com.manneung.careerup.domain.file.model.File;
import com.manneung.careerup.domain.file.repository.FileRepository;
import com.manneung.careerup.global.s3.S3UploaderService;
import org.junit.jupiter.api.Test;


import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class FileServiceTest {

    private FileRepository fileRepository;
    private FileService fileService;

    @Transactional
    @Test
    public void testDeleteFile() {
        // Initialize the fileRepository and fileService variables
        fileRepository = mock(FileRepository.class);
        S3UploaderService s3UploaderService = mock(S3UploaderService.class);

        // Initialize the fileService variable
        fileService = new FileService(fileRepository, s3UploaderService);


        // Save a file to the database
        File file = new File();
        file.setItemIdx(1);
        file.setFileType("type1");
        file.setFileName("name1");
        fileRepository.save(file);

        // Get the fileIdx of the saved file
        int fileIdx = file.getFileIdx();

        // Call the deleteFile method
        Boolean result = fileService.deleteFile(fileIdx);

        // Check if the file was successfully deleted
        assertEquals(Boolean.TRUE, result);
        assertNull(fileRepository.findById(fileIdx).orElse(null));
    }

    @Transactional
    @Test
    public void testUpdateFile() {
        fileRepository = mock(FileRepository.class);
        S3UploaderService s3UploaderService = mock(S3UploaderService.class);

        // Initialize the fileService variable
        fileService = new FileService(fileRepository, s3UploaderService);

        // Save a file to the database
        File file = new File();
        file.setItemIdx(1);
        file.setFileType("type1");
        file.setFileName("name1");

        // Set up the mock repository to return the saved file when findById is called
        when(fileRepository.findById(anyInt())).thenReturn(Optional.of(file));
        // Set up the mock repository to return the updated file when save is called
        when(fileRepository.save(any(File.class))).thenReturn(file);

        // Get the fileIdx of the saved file
        int fileIdx = file.getFileIdx();

        // Create a new File object with updated values
        File updatedFile = new File();
        updatedFile.setFileType("type2");
        updatedFile.setFileName("name2");

        // Call the updateFile method
        File result = fileService.updateFile(fileIdx, updatedFile);

        // Check if the file was successfully updated
        assertNotNull(result);
        assertEquals("type2", result.getFileType());
        assertEquals("name2", result.getFileName());
    }

}