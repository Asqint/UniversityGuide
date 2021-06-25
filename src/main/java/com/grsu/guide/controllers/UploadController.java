package com.grsu.guide.controllers;

import com.grsu.guide.service.ElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class UploadController {

    private final ElementService elementService;

    @Autowired
    public UploadController(ElementService elementService) {
        this.elementService = elementService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam(required = false) MultipartFile file) throws IOException {
        String path = elementService.uploadElement(file,uploadPath);
        String location = "{ \"location\":\"../uploads/"+path+"\"}";
        return ResponseEntity.ok(location);
    }
}
