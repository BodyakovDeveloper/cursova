package com.team.managing.controller;

import com.team.managing.captcha.ICaptchaService;
import com.team.managing.entity.UserEntity;
import com.team.managing.service.RoleDaoService;
import com.team.managing.service.UserDaoService;
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

    private final RoleDaoService roleDaoService;
    private final UserDaoService userDaoService;
    private final PasswordEncoder passwordEncoder;
    private final ICaptchaService captchaService;

    public RegistrationController(RoleDaoService roleDaoService, UserDaoService userDaoService,
                                  PasswordEncoder passwordEncoder, ICaptchaService captchaService) {
        this.roleDaoService = roleDaoService;
        this.userDaoService = userDaoService;
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

        user.setRoleEntity(roleDaoService.getRoleByName("USER"));

        if (userDaoService.isUserExists(user.getLogin(), user.getEmail())) {
            model.addAttribute("errorMessage", USER_IS_ALREADY_EXIST_MESSAGE);
            return "signup";
        }

        if (captchaService.processResponse(recaptchaResponse)) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoleEntity(roleDaoService.getRoleByName(ROLE_USER));
            userDaoService.create(user);

            return "redirect:/login";
        } else {
            model.addAttribute("errorMessage", CAPTCHA_RESPONSE_ERROR);
            return "/signup";
        }
    }
}