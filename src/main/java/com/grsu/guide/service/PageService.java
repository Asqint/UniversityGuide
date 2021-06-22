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

    public Page getPage (String urlPage){
        return pageRepository.findByUrlPage(urlPage);
    }

    public Iterable<Page> getAllPages(){
        return pageRepository.findAll();
    }

    public void addPage(Page page){

        if(page.getElements()!=null) {
            for (Element newElement : page.getElements()) {
                page.getElements().add(newElement);
            }
            page.setElements(page.getElements());
        }

        pageRepository.save(page);
    }

    public void deletePage(String urlPage){
        pageRepository.deleteById(getPage(urlPage).getId());
    }


}

