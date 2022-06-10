package com.team.managing.controller;

import com.team.managing.entity.UserEntity;
import com.team.managing.exception.UserValidationException;
import com.team.managing.service.RoleService;
import com.team.managing.service.UserService;
import org.springframework.security.core.Authentication;
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

    private final RoleService roleService;
    private final UserService userService;

    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/main_page")
    public String getMainPage(Model model, Authentication authentication) {

        UserEntity userEntity = userService.findByLogin(authentication.getName());
        model.addAttribute("userName", userEntity.getFirstName());
        model.addAttribute("users", userService.findAllUsers());

        return "/admin_pages/main_page";
    }

    @GetMapping("/add_user")
    public String getAddUserPage(Model model) {

        model.addAttribute("roles", roleService.findAllRoles());
        return "/admin_pages/add_user";
    }

    @PostMapping("/add_user")
    public String addNewUser(@ModelAttribute("user") UserEntity user,
                             @RequestParam String roleName,
                             Model model) {

        if (userService.isUserExists(user.getLogin(), user.getEmail())) {

            model.addAttribute("errorMessage", USER_IS_ALREADY_EXIST_MESSAGE);
            return "/admin_pages/add_user";
        }
        user.setRoleEntity(roleService.getRoleByName(roleName));

        try {
            userService.create(user);
        } catch (UserValidationException e) {
            model.addAttribute("errorMessage", "Wrong input, try again");
            return "/admin_pages/add_user";
        }

        return "redirect:/admin/main_page";
    }

    @GetMapping("/edit_user")
    public String getEditUserPage(@RequestParam String login, Model model) {

        UserEntity userToEdit = userService.findByLogin(login);
        model.addAttribute("userToEdit", userToEdit);
        model.addAttribute("roles", roleService.findAllRoles());

        return "/admin_pages/edit_user";
    }

    @PostMapping("/edit_user")
    public String editUser(@ModelAttribute("userToEdit") UserEntity userFromHtmlToEdit,
                           @RequestParam String roleName, Model model) {

        UserEntity userFromDbToEdit = userService.findByLogin(userFromHtmlToEdit.getLogin());

        boolean isUserAlreadyExistsInDb = !userFromDbToEdit.getEmail().equals(userFromHtmlToEdit.getEmail())
                && userService.findByEmail(userFromHtmlToEdit.getEmail()) != null;

        if (isUserAlreadyExistsInDb) {

            model.addAttribute("roles", roleService.findAllRoles());
            model.addAttribute("errorMessage", USER_IS_ALREADY_EXIST_MESSAGE);
            return "/admin_pages/edit_user";
        }
        userFromHtmlToEdit = userService.setFieldsUserToEdit(userFromDbToEdit, userFromHtmlToEdit, roleName);
        try {
            userService.update(userFromHtmlToEdit);
        } catch (UserValidationException e) {
            model.addAttribute("errorMessage", "Wrong input, try again");
            model.addAttribute("roles", roleService.findAllRoles());
            return "/admin_pages/edit_user";
        }

        return "redirect:/admin/main_page";
    }

    @GetMapping("/delete_user")
    public String deleteUser(@RequestParam String login) {
        UserEntity user = userService.findByLogin(login);
        userService.remove(user);

        return "redirect:/admin/main_page";
    }
}