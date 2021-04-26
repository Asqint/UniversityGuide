package com.grsu.guide.controllers;

import com.grsu.guide.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {

    @GetMapping("/home")
        public String home(Model model){
        Page pageOne = new Page();
        List<String> list = new ArrayList<>();
        list.add("First");
        list.add("Second");
        list.add("Third");
        list.add("Fourth");
        list.add("Fifth");
        model.addAttribute("list", list);
        return "home";
    }
}
