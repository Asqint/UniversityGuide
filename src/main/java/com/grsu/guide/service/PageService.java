package com.grsu.guide.service;

import com.grsu.guide.domain.Page;
import com.grsu.guide.repository.PageRepository;
import org.springframework.stereotype.Service;

@Service
public class PageService {
   private final PageRepository pageRepository;

    public PageService(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    public Page GetPage (String namePage){
        return pageRepository.findByNamePage(namePage);
    }

    public Iterable<Page> GetAllPages(){
        return pageRepository.findAll();
    }

    public void AddPage(Page page){
        pageRepository.save(page);
    }
    
}

