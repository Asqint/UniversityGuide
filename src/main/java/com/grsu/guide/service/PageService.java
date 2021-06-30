package com.grsu.guide.service;

import com.grsu.guide.domain.Element;
import com.grsu.guide.domain.Page;
import com.grsu.guide.repository.PageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import java.util.List;

@Service
public class PageService {
   private final PageRepository pageRepository;

    public PageService(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    public Optional<Page> getPage (Long id){
        return pageRepository.findById(id);
    }

    public List<Page> getAllChildPages(Long id) {return  pageRepository.findPagesByParentPageId(id);}

    public List<Page> getAllParentPages(Long id){
        return pageRepository.findPagesByParentPageId(id);
    }

    public void savePage(Page page){
        pageRepository.save(page);
    }

    public boolean deletePage(Long id){
        List<Page> childPages = getAllChildPages(id);
        if (childPages.isEmpty()) {
            pageRepository.deleteById(id);
            return true;
        }
        else {
            Page page = getPage(id).orElseGet(Page::new);
            if(page.getParentPageId()!=0L) {
                Long pageParentPageId = page.getParentPageId();
                for (Page childPage:childPages) {
                    childPage.setParentPageId(pageParentPageId);
                    savePage(childPage);
                }
                pageRepository.deleteById(id);
                return true;
            }
            else {
                return false;
            }
        }
    }

    public List<Page> getPagesBySearchRequest(String searchRequest) {
        return pageRepository.findPagesByNamePageContainsIgnoreCase(searchRequest);
    }

    public List<Page> getHierarchyPages(Long currentPageId) {
        List<Page> hierarchyPages = new ArrayList<>();
        Page page;
        while(currentPageId!=0) {
            page = getPage(currentPageId).orElseGet(Page::new);
            hierarchyPages.add(page);
            currentPageId = page.getParentPageId();
        }
        Collections.reverse(hierarchyPages);
        return hierarchyPages;
    }
}

