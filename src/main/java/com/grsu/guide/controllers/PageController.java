package com.grsu.guide.controllers;

import com.grsu.guide.domain.*;
import com.grsu.guide.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.*;

@Controller
public class PageController {

    private final SmtpMailSender mailSender;
    private final PageService pageService;
    private final ElementService elementService;
    private final UserService userService;
    private final FeedbackService feedbackService;
    private final TemplateService templateService;

    @Autowired
    public PageController(SmtpMailSender mailSender, PageService pageService, ElementService elementService, UserService userService, FeedbackService feedbackService, TemplateService templateService){
        this.mailSender = mailSender;
        this.pageService = pageService;
        this.elementService = elementService;
        this.userService = userService;
        this.feedbackService = feedbackService;
        this.templateService = templateService;
    }


    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
        public String main(Model model){
        List<Page> parentPages = pageService.getAllParentPages(0L);
        Long pageId = parentPages.get(0).getId();
        return "redirect:/page/"+pageId;
    }

    @GetMapping("/page/{pageId}")
    public String getPage(@PathVariable Long pageId,
                          Principal user,
                          Model model,
                          @RequestParam(required = false) boolean rootPage){
        Page page = pageService.getPage(pageId).orElseGet(Page::new);
        if(page.getParentPageId()!=0L) {
            List<Page> hierarchyPages = pageService.getHierarchyPages(page.getParentPageId());
            model.addAttribute("hierarchyPages", hierarchyPages);
        }
        List<Page> parentPages = pageService.getAllParentPages(0L);
        List<Page> childPages = pageService.getAllChildPages(pageId);
        List <Element> sortedList = new ArrayList<>(page.getElements());
        if(user != null){
            List<Template> templates = templateService.findAllTemplatesById(userService.findUserByUserName(user.getName()).getId());
            model.addAttribute("templates", templates);
        }
        sortedList.sort(Comparator.comparing(Element::getId));
        if (rootPage) {
            model.addAttribute("rootPage",true);
        }
        model.addAttribute("elements",sortedList );
        model.addAttribute("parentPages", parentPages);
        model.addAttribute("childPages", childPages);
        model.addAttribute("page", page);
        return "home";
    }


    @PostMapping("/page/{parentId}/add_child_page")
    public String addChildPage(@PathVariable Long parentId,
                               @RequestParam String newNamePage) {
        Page childPage = new Page();
        childPage.setNamePage(newNamePage);
        childPage.setParentPageId(parentId);
        pageService.savePage(childPage);
        return "redirect:/page/{parentId}";
    }


    @PostMapping("/add")
    public String addPage(@RequestParam(required = false) String newNamePage){
        Page page = new Page();
        page.setNamePage(newNamePage);
        page.setParentPageId(0L);
        pageService.savePage(page);
        return "redirect:/";
    }

    @PostMapping("/page/{pageId}/add_template")
    public String addTemplate(@RequestParam(required=false) Long id,
                              @RequestParam(required=false) String name,
                              Principal user) {
        Element element = elementService.getElement(id).orElseGet(Element::new);
        Template template = new Template(name,element.getValue(),userService.findUserByUserName(user.getName()).getId());
        templateService.addTemplate(template);
        return "redirect:/page/{pageId}";
    }

    @PostMapping("/page/{pageId}/edit_template")
    public String editTemplate(@RequestParam(required = false) Long id,
                               @RequestParam(required = false) String name){
        Template template = templateService.findById(id).orElseGet(Template::new);
        template.setName(name);
        templateService.addTemplate(template);
        return "redirect:/page/{pageId}";
    }

    @PostMapping("/page/{pageId}/delete_template")
    public String deleteTemplate(@RequestParam(required = false) Long id){
        templateService.deleteTemplate(id);
        return "redirect:/page/{pageId}";
    }

    @PostMapping("/page/{pageId}/add_el")
    public String addElement(@RequestParam(required=false) String value,
                             @PathVariable Long pageId,
                             Principal principal)
    {
        Page page = pageService.getPage(pageId).orElseGet(Page::new);
        Element element = new Element();
        element.setValue(value);
        element.setEditor("Added by "+principal.getName()+" - "+new Timestamp(System.currentTimeMillis()));
        List<Element> elements = page.getElements();
        elements.add(element);
        page.setElements(elements);
        pageService.savePage(page);
        return "redirect:/page/{pageId}";
    }

    @PostMapping("/page/{pageId}/edit")
    public String editPage(@RequestParam(required = false) String newNamePage,
                           @PathVariable Long pageId)
    {
        Page page = pageService.getPage(pageId).orElseGet(Page::new);
        page.setNamePage(newNamePage);
        pageService.savePage(page);
        return "redirect:/";
    }

    @PostMapping("/page/{pageId}/edit_el/{id}")
    public String editElement(@PathVariable Long id,
                              @PathVariable Long pageId,
                              @RequestParam(required = false) String value,
                              Principal principal)
    {
        Element element = elementService.getElement(id).orElseGet(Element::new);
        Page page = pageService.getPage(pageId).orElseGet(Page::new);
        element.setValue(value);
        element.setEditor("Edited by "+principal.getName()+" - "+new Timestamp(System.currentTimeMillis()));
        List<Element> elements = page.getElements();
        elements.add(element);
        page.setElements(elements);
        pageService.savePage(page);
        return "redirect:/page/{pageId}";
    }

    @PostMapping("/page/{pageId}/delete")
    public String deletePage(@PathVariable Long pageId, RedirectAttributes redirectAttributes){
        if(!pageService.deletePage(pageId)){
            redirectAttributes.addFlashAttribute("rootPage",true);
            return "redirect:/page/{pageId}";
        }
        return "redirect:/";
    }

    @PostMapping("/page/{pageId}/delete_el/{id}")
    public String deleteElement(@PathVariable Long id, @PathVariable String pageId){
        elementService.deleteElement(id);
        return "redirect:/page/{pageId}";
    }

    @GetMapping("/feedback")
    public String getFeedback(Model model) {
        List<Page> pages = pageService.getAllParentPages(0L);
        model.addAttribute("pages", pages);
        return "feedback";
    }


    @PostMapping("/feedback")
    public String feedback(@RequestParam(required = false) String name,
                           @RequestParam(required = false) String mail,
                           @RequestParam(required = false) String message,
                           Model model){
        List<Page> pages = pageService.getAllParentPages(0L);
        model.addAttribute("pages", pages);

        Feedback feedback = new Feedback(name, mail, message);
        model.addAttribute("isSent", 2);
        String messageTo = String.format(
                "Email: %s \n" +
                        "Name: %s \n" +
                       "Message: %s",
                mail,name,message

        );

        feedbackService.saveFeedback(feedback);
        try {
            mailSender.send("Feedback", messageTo);
        }
        catch (Exception e){
            model.addAttribute("isSent", 0);
        }


        model.addAttribute("isSent", 1);
        return "feedback";
    }

    @GetMapping("/search")
    public String getSearch(@RequestParam(required = false) String searchRequest, Model model) {
        List<Page> pages = pageService.getAllParentPages(0L);
        model.addAttribute("pages", pages);
        if(searchRequest != null){
            List<Page> result = pageService.getPagesBySearchRequest(searchRequest);
            model.addAttribute("searchRequest", searchRequest);
            model.addAttribute("result", result);
        }
        return "search";
    }
}
