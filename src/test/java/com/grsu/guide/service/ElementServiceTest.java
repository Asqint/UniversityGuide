package com.grsu.guide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.InvalidPathException;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class ElementServiceTest {

    @Autowired
    private ElementService elementService;

    @Value("${upload.path}")
    private String uploadPath;


    @Test
    void uploadElement() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "filename.jpg",
                MediaType.IMAGE_JPEG_VALUE, "filename.jpg".getBytes());
        try {
            Assertions.assertTrue(elementService.uploadElement(file,uploadPath).contains(file.getOriginalFilename()));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}