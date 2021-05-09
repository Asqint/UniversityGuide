package com.grsu.guide.service;

import com.grsu.guide.domain.Element;
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

        if(page.getElements()!=null) {
            for (Element newElement : page.getElements()) {
                page.getElements().add(newElement);
            }
            page.setElements(page.getElements());
        }

        pageRepository.save(page);
    }

    public void EditPage(Page page){
        pageRepository.save(page);
    }

    public void DeletePage(String namePage){
        pageRepository.deleteById(GetPage(namePage).getId());
    }


}

