package com.team.managing.controller;

import com.team.managing.entity.UserEntity;
import com.team.managing.service.RoleDaoService;
import com.team.managing.service.UserDaoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.team.managing.constant.ConstantClass.USER_IS_ALREADY_EXIST_MESSAGE;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleDaoService roleDaoService;
    private final UserDaoService userDaoService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(RoleDaoService roleDaoService,
                           UserDaoService userDaoService, PasswordEncoder passwordEncoder) {
        this.roleDaoService = roleDaoService;
        this.userDaoService = userDaoService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/main_page")
    public String getMainPage(Model model, Authentication authentication) {

        UserEntity userEntity = userDaoService.findByLogin(authentication.getName());
        model.addAttribute("userName", userEntity.getFirstName());
        model.addAttribute("users", userDaoService.findAllUsers());

        return "/admin_pages/main_page";
    }

    @GetMapping("/add_user")
    public String getAddUserPage(Model model) {

        model.addAttribute("roles", roleDaoService.findAllRoles());
        return "/admin_pages/add_user";
    }

    @PostMapping("/add_user")
    public String addNewUser(@ModelAttribute("user") UserEntity user,
                             @RequestParam String roleName,
                             Model model) {

        if (userDaoService.isUserExists(user.getLogin(), user.getEmail())) {

            model.addAttribute("errorMessage", USER_IS_ALREADY_EXIST_MESSAGE);
            return "/admin_pages/add_user";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleEntity(roleDaoService.getRoleByName(roleName));
        userDaoService.create(user);

        return "redirect:/admin/main_page";
    }

    @GetMapping("/edit_user")
    public String getEditUserPage(@RequestParam String login, Model model) {

        UserEntity userToEdit = userDaoService.findByLogin(login);
        model.addAttribute("userToEdit", userToEdit);
        model.addAttribute("roles", roleDaoService.findAllRoles());

        return "/admin_pages/edit_user";
    }

    @PostMapping("/edit_user")
    public String editUser(@ModelAttribute("userToEdit") UserEntity userFromHtmlToEdit,
                           @RequestParam String roleName, Model model) {

        UserEntity userFromDbToEdit = userDaoService.findByLogin(userFromHtmlToEdit.getLogin());

        if (!userFromDbToEdit.getEmail().equals(userFromHtmlToEdit.getEmail())
                && userDaoService.findByEmail(userFromHtmlToEdit.getEmail()) != null) {

            model.addAttribute("roles", roleDaoService.findAllRoles());
            model.addAttribute("errorMessage", USER_IS_ALREADY_EXIST_MESSAGE);
            return "/admin_pages/edit_user";
        }
        userFromHtmlToEdit = userDaoService.setFieldsUserToEdit(userFromDbToEdit, userFromHtmlToEdit, roleName);
        userDaoService.update(userFromHtmlToEdit);

        return "redirect:/admin/main_page";
    }

    @GetMapping("/delete_user")
    public String deleteUser(@RequestParam String login) {
        UserEntity user = userDaoService.findByLogin(login);
        userDaoService.remove(user);

        return "redirect:/admin/main_page";
    }
}