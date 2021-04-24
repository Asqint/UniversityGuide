package com.grsu.guide.service;

import com.grsu.guide.domain.Audio;
import com.grsu.guide.domain.Page;
import com.grsu.guide.domain.Text;
import com.grsu.guide.domain.Video;
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

        if(page.getTexts()!= null) {
            for (Text newText : page.getTexts()) {
                newText.setPage(page);
                page.getTexts().add(newText);
            }
            page.setTexts(page.getTexts());
        }

        if(page.getVideos()!=null) {
            for (Video newVideo : page.getVideos()) {
                newVideo.setPage(page);
                page.getVideos().add(newVideo);
            }
            page.setVideos(page.getVideos());
        }

        if(page.getAudios()!=null) {
            for (Audio newAudio : page.getAudios()) {
                newAudio.setPage(page);
                page.getAudios().add(newAudio);
            }
            page.setAudios(page.getAudios());
        }

        pageRepository.save(page);
    }

    public void EditPage(Page page){
        pageRepository.save(page);
    }

    public void DeletePage(Page page){
        pageRepository.deleteById(page.getId());
    }

}

