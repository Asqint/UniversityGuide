package com.grsu.guide.service;

import com.grsu.guide.domain.Element;
import com.grsu.guide.domain.Page;
import com.grsu.guide.repository.ElementRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ElementService {

    private final ElementRepository elementRepository;

    public ElementService(ElementRepository elementRepository) {
        this.elementRepository = elementRepository;
    }

    public Optional<Element> GetElement(Long id){
        return elementRepository.findById(id);
    }

    public void DeleteElement(Long id){ elementRepository.deleteById(id);}

    public String UploadElement(MultipartFile file, String uploadPath) throws IOException {
        File uploadFolder = new File(uploadPath);
        if(!uploadFolder.exists()){
            uploadFolder.mkdir();
        }
        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();

        file.transferTo(new File(uploadPath + "/" + resultFilename));
        return resultFilename;
    }



}
