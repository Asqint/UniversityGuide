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
        public String main(Model model){
        List<Page> parentPages = (List<Page>) pageService.getAllParentPages(0L);
        Long pageId = parentPages.get(0).getId();
        return "redirect:/page/"+pageId;
    }

    @GetMapping("/page/{pageId}")
    public String getPage(@PathVariable Long pageId, Model model){
        Optional<Page> optionalPage = pageService.getPage(pageId);
        if(optionalPage.get().getParentPageId() != 0L){
            Optional<Page> optionalParentPage = pageService.getPage(optionalPage.get().getParentPageId());
            Page parentPage = optionalParentPage.get();
            model.addAttribute("parentPage", parentPage);
        }

        Page page = optionalPage.get();
        List<Page> parentPages = (List<Page>) pageService.getAllParentPages(0L);
        List<Page> childPages = (List<Page>) pageService.getAllChildPages(pageId);
        List <Element> sortedList = new ArrayList<>(page.getElements());

        sortedList.sort(Comparator.comparing(Element::getId));
        model.addAttribute("elements",sortedList );
        model.addAttribute("parentPages", parentPages);
        model.addAttribute("childPages", childPages);
        model.addAttribute("page", page);
        return "home";
    }


    @PostMapping("/page/{parentId}/add_child_page")
    public String addChildPage(@PathVariable Long parentId,
                               @RequestParam String newNamePage){
        Page childPage = new Page();
        childPage.setNamePage(newNamePage);
        childPage.setParentPageId(parentId);
        pageService.addPage(childPage);
        return "redirect:/page/{parentId}";
    }


    @PostMapping("/add")
    public String addPage(@RequestParam(required = false) String newNamePage){
        Page page = new Page();
        page.setNamePage(newNamePage);
        page.setParentPageId(0L);
        pageService.addPage(page);
        return "redirect:/";
    }


    @PostMapping("/page/{pageId}/add_el")
    public String addElement(@RequestParam(required=false) String value,
                             @RequestParam(required=false) String type,
                             @PathVariable Long pageId,
                             @RequestParam(value = "file", required = false) MultipartFile file
                             ) throws IOException {
        Optional<Page> optionalPage = pageService.getPage(pageId);
        Page page = optionalPage.get();
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
                return "redirect:/page/{pageId}";
            }
        }
        element.setType(type);
        Set<Element> elements = page.getElements();
        elements.add(element);
        page.setElements(elements);
        pageService.addPage(page);
        return "redirect:/page/{pageId}";
    }

    @PostMapping("/page/{pageId}/edit")
    public String editPage(@RequestParam(required = false) String newNamePage,
                           @PathVariable Long pageId){
        Optional<Page> optionalPage = pageService.getPage(pageId);
        Page page = optionalPage.get();
        page.setNamePage(newNamePage);
        pageService.addPage(page);
        return "redirect:/";
    }

    @PostMapping("/page/{pageId}/edit_el/{id}")
    public String editElement(@PathVariable Long id,
                              @PathVariable Long pageId,
                              @RequestParam(required = false) String type,
                              @RequestParam(required = false) String value,
                              @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        Optional<Page> optionalPage = pageService.getPage(pageId);
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
                return "redirect:/page/{pageId}";
            }
        }
        Page page = optionalPage.get();
        Element element = optionalElement.get();
        Set<Element> elements = page.getElements();
        elements.add(element);
        page.setElements(elements);
        pageService.addPage(page);
        return "redirect:/page/{pageId}";
    }

    @PostMapping("/page/{pageId}/delete")
    public String deletePage(@PathVariable Long pageId){
        pageService.deletePage(pageId);
        return "redirect:/";
    }

    @PostMapping("/page/{pageId}/delete_el/{id}")
    public String deleteElement(@PathVariable Long pageId, @PathVariable Long id){
        elementService.deleteElement(id);
        return "redirect:/page/{pageId}";
    }

    @GetMapping("/feedback")
    public String getFeedback(Model model) {
        List<Page> pages = (List<Page>) pageService.getAllParentPages(0L);
        model.addAttribute("pages", pages);
        model.addAttribute("isSent", false);
        return "feedback";
    }


    @PostMapping("/feedback")
    public String feedback(@RequestParam(required = false) String name,
                           @RequestParam(required = false) String mail,
                           @RequestParam(required = false) String message,
                           Model model){
        List<Page> pages = (List<Page>) pageService.getAllParentPages(0L);
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

    @GetMapping("/search")
    public String getSearch(@RequestParam(required = false) String searchRequest, Model model) {
        List<Page> pages = (List<Page>) pageService.getAllParentPages(0L);
        model.addAttribute("pages", pages);
        if(searchRequest != null){
            List<Page> result = pageService.getPagesBySearchRequest(searchRequest);
            model.addAttribute("searchRequest", searchRequest);
            model.addAttribute("result", result);
        }
        return "search";
    }

}
