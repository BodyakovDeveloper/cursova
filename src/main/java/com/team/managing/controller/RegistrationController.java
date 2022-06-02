package com.team.managing.controller;

import com.team.managing.captcha.ICaptchaService;
import com.team.managing.entity.UserEntity;
import com.team.managing.exception.UserValidationException;
import com.team.managing.service.RoleService;
import com.team.managing.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.team.managing.constant.ConstantClass.CAPTCHA_RESPONSE_ERROR;
import static com.team.managing.constant.ConstantClass.ROLE_USER;
import static com.team.managing.constant.ConstantClass.USER_IS_ALREADY_EXIST_MESSAGE;

@Controller
public class RegistrationController {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ICaptchaService captchaService;

    public RegistrationController(RoleService roleService, UserService userService,
                                  PasswordEncoder passwordEncoder, ICaptchaService captchaService) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.captchaService = captchaService;
    }

    @GetMapping("/signup")
    public String getRegistrationPage() {
        return "/signup";
    }

    @PostMapping("/signup")
    public String addNewUser(@ModelAttribute("userToRegister") UserEntity user,
                             @RequestParam("g-recaptcha-response") String recaptchaResponse, Model model) {

        user.setRoleEntity(roleService.getRoleByName("USER"));

        if (userService.isUserExists(user.getLogin(), user.getEmail())) {
            model.addAttribute("errorMessage", USER_IS_ALREADY_EXIST_MESSAGE);
            return "signup";
        }

        if (captchaService.processResponse(recaptchaResponse)) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoleEntity(roleService.getRoleByName(ROLE_USER));
            try {
                userService.create(user);
            } catch (UserValidationException e) {
                model.addAttribute("errorMessage", "Wrong input, try again");
                return "signup";
            }

            return "redirect:/login";
        } else {
            model.addAttribute("errorMessage", CAPTCHA_RESPONSE_ERROR);
            return "/signup";
        }
    }
}