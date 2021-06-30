package com.grsu.guide.controllers;

import com.grsu.guide.domain.Page;
import com.grsu.guide.domain.Role;
import com.grsu.guide.domain.User;
import com.grsu.guide.service.PageService;
import com.grsu.guide.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final PageService pageService;

    public UserController(UserService userService, PageService pageService) {
        this.userService = userService;
        this.pageService = pageService;
    }

    @GetMapping("/users")
    private String getUsers(Model model){
        List<Page> pages =  pageService.getAllParentPages(0L);
        model.addAttribute("pages", pages);
        model.addAttribute("errorName", false);
        model.addAttribute("users", userService.findAllUsers());
        return "users";
    }

    @PostMapping("/add_user")
    private String saveUser(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String role,
                           @RequestParam(required = false) Long id,
                           Model model){
        List<Page> pages =  pageService.getAllParentPages(0L);
        model.addAttribute("pages", pages);
        model.addAttribute("users", userService.findAllUsers());

        if(userService.findUserByUserName(username).getUsername() != null){
            model.addAttribute("errorName",true);

            return "users";
        }
        User user = new User();
        if(id != null){
            user.setId(id);
        }
        user.setRoles(Collections.singleton(new Role(role)));
        user.setUserName(username);
        user.setPassword(password);
        userService.saveUser(user);

        return "users";
    }


    @PostMapping("/delete_user")
    public String deleteUser(@RequestParam Long id){
        userService.deleteUser(id);
        return "redirect:/users";
    }

}
