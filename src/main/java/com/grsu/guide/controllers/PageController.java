package com.grsu.guide.controllers;

import com.grsu.guide.domain.Element;
import com.grsu.guide.domain.Page;
import com.grsu.guide.domain.User;
import com.grsu.guide.service.ElementService;
import com.grsu.guide.service.PageService;
import com.grsu.guide.service.SmtpMailSender;
import com.grsu.guide.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
public class PageController {

    private final SmtpMailSender mailSender;
    private final PageService pageService;
    private final ElementService elementService;
    private final UserService userService;

    @Autowired
    public PageController(SmtpMailSender mailSender, PageService pageService, ElementService elementService, UserService userService){
        this.mailSender = mailSender;
        this.pageService = pageService;
        this.elementService = elementService;
        this.userService = userService;
    }


    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
        public String main( Model model){
        List<Page> pages = (List<Page>) pageService.getAllPages();
        List <Element> sortedList = new ArrayList<>(pages.get(0).getElements());
        sortedList.sort(Comparator.comparing(Element::getId));
        model.addAttribute("elements",sortedList);
        model.addAttribute("pages", pages);
        model.addAttribute("page", pages.get(0));
        return "home";
    }

    @GetMapping("/{urlPage}")
    public String getPage(@PathVariable String urlPage, Model model){
        Page page = pageService.getPage(urlPage);
        List<Page> pages = (List<Page>) pageService.getAllPages();
        List <Element> sortedList = new ArrayList<>(page.getElements());
        sortedList.sort(Comparator.comparing(Element::getId));
        model.addAttribute("elements",sortedList );
        model.addAttribute("pages", pages);
        model.addAttribute("page", page);
        return "home";
    }


    @PostMapping("/add")
    public String addPage(@RequestParam(required = false) String newNamePage){
        Page page = new Page();
        page.setNamePage(newNamePage);
        page.setUrlPage(UUID.randomUUID().toString());
        pageService.addPage(page);
        return "redirect:/";
    }


    @PostMapping("/{urlPage}/add_el")
    public String addElement(@RequestParam(required=false) String value,
                             @RequestParam(required=false) String type,
                             @PathVariable String urlPage,
                             @RequestParam(value = "file", required = false) MultipartFile file
                             ) throws IOException {
        Page page = pageService.getPage(urlPage);
        Element element = new Element();

        if(file !=null && !file.getOriginalFilename().isEmpty()){
             element.setValue(elementService.uploadElement(file,uploadPath));
        }
        else{
            if(type.equals("text") && value!=null) {
                element.setValue(value);
            }
            else
            {
                return "redirect:/{urlPage}";
            }
        }
        element.setType(type);
        Set<Element> elements = page.getElements();
        elements.add(element);
        page.setElements(elements);
        pageService.addPage(page);
        return "redirect:/{urlPage}";
    }

    @PostMapping("/{urlPage}/edit")
    public String editPage(@RequestParam(required = false) String newNamePage,
                           @PathVariable String urlPage){
        Page page = pageService.getPage(urlPage);
        page.setNamePage(newNamePage);
        pageService.addPage(page);
        return "redirect:/";
    }

    @PostMapping("/{urlPage}/edit_el/{id}")
    public String editElement(@PathVariable Long id,
                              @RequestParam(required = false) String type,
                              @RequestParam(required = false) String value,
                              @PathVariable String urlPage,
                              @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        Page page = pageService.getPage(urlPage);

        Optional<Element> optionalElement = elementService.getElement(id);
        if(file !=null && !file.getOriginalFilename().isEmpty()){
            optionalElement.get().setValue(elementService.uploadElement(file,uploadPath));
            optionalElement.get().setType(type);
        }
        else{
            if(type.equals("text")) {
                optionalElement.get().setType(type);
                optionalElement.get().setValue(value);
            }
            else
            {
                return "redirect:/{urlPage}";
            }
        }
        Element element = optionalElement.get();
        Set<Element> elements = page.getElements();
        elements.add(element);
        page.setElements(elements);
        pageService.addPage(page);
        return "redirect:/{urlPage}";
    }

    @PostMapping("/{urlPage}/delete")
    public String deletePage(@PathVariable String urlPage){
        pageService.deletePage(urlPage);
        return "redirect:/";
    }

    @PostMapping("/{urlPage}/delete_el/{id}")
    public String deleteElement(@PathVariable String urlPage, @PathVariable Long id){
        elementService.deleteElement(id);
        return "redirect:/{urlPage}";
    }

    @GetMapping("/feedback")
    public String getFeedback(Model model) {
        List<Page> pages = (List<Page>) pageService.getAllPages();
        model.addAttribute("pages", pages);
        model.addAttribute("isSent", false);
        return "feedback";
    }


    @PostMapping("/feedback")
    public String feedback(@RequestParam(required = false) String name,
                           @RequestParam(required = false) String mail,
                           @RequestParam(required = false) String message,
                           Model model){
        List<Page> pages = (List<Page>) pageService.getAllPages();
        model.addAttribute("pages", pages);

        User user = new User(name, mail, message);

        String messageTo = String.format(
                "Email: %s \n" +
                        "Name: %s \n" +
                       "Message: %s",
                mail,name,message

        );

        userService.saveUser(user);
        mailSender.send("Feedback", messageTo);

        model.addAttribute("isSent", true);
        return "feedback";
    }


}
