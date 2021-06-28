package com.grsu.guide.service;

import com.grsu.guide.domain.Template;
import com.grsu.guide.repository.TemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TemplateService {

    private final TemplateRepository templateRepository;

    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public List<Template> findAllTemplatesById(Long id){
        return templateRepository.findTemplatesByUserId(id);
    }

    public Optional<Template> findById(Long id){ return templateRepository.findById(id);}

    public void deleteTemplate(Long id){ templateRepository.deleteById(id);}

    public void addTemplate(Template template){ templateRepository.save(template);}
}
