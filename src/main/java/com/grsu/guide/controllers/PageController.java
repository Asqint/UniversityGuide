package com.grsu.guide.controllers;

import com.grsu.guide.domain.Element;
import com.grsu.guide.domain.Page;
import com.grsu.guide.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class PageController {

    private final PageService pageService;

    @Autowired
    public PageController(PageService pageService){
        this.pageService = pageService;
    }

    @GetMapping("/{namePage}")
        public String GetPage(@PathVariable(value = "namePage") String namePage, Model model){
       Page page = pageService.GetPage(namePage);
       List<Page> pages = (List<Page>) pageService.GetAllPages();
        model.addAttribute("page", page);
        model.addAttribute("pages", pages);
        return "main";
    }
}
