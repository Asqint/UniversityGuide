package com.grsu.guide.service;

import com.grsu.guide.domain.Element;
import com.grsu.guide.domain.Page;
import com.grsu.guide.repository.ElementRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

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



}
