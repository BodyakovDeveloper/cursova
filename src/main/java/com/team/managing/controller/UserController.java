package com.team.managing.controller;

import com.team.managing.entity.UserEntity;
import com.team.managing.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/main_page")
    public String getUserMainPage(Model model, Authentication authentication) {
        UserEntity userEntity = userService.findByLogin(authentication.getName());
        model.addAttribute("userName", userEntity.getFirstName());

        return "/user_pages/main_page";
    }
}