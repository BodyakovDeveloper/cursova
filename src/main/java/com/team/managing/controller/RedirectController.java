package com.team.managing.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.team.managing.constant.ConstantClass.AUTHENTICATION_ROLE_ADMIN;
import static com.team.managing.constant.ConstantClass.AUTHENTICATION_ROLE_USER;

@Controller
public class RedirectController {

    @GetMapping("/redirect")
    public String login(Authentication authentication) {

        if (authentication.getAuthorities().toString().equals(AUTHENTICATION_ROLE_USER)) {
            return "redirect:/user/main_page";
        } else if (authentication.getAuthorities().toString().equals(AUTHENTICATION_ROLE_ADMIN)) {
            return "redirect:/admin/main_page";
        } else {
            return "redirect:/logout";
        }
    }
}