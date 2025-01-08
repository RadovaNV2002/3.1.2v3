package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.valid.UserValidator;

import javax.validation.Valid;
import java.util.Collection;

@Controller
public class AdminController {

    private final UserValidator userValidator;
    private UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserValidator userValidator, UserService userService, RoleService roleService) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showUsers(Model model) {
        model.addAttribute("allUsers", userService.showUsers());
        return "admin";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findById(id).orElse(null));
        model.addAttribute("roles", roleService.getAllRoles());
        return "edit";
    }

    @PatchMapping("/edit/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         @PathVariable("id") Long id, @RequestParam("selectedRole") Collection<Role> selectedRole) {
        user.setRoles(selectedRole);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String registration(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("userForm", new User());
        return "new";
    }

    @PostMapping("/new")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model, @RequestParam("selectedRole") Collection<Role> selectedRole) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "new";
        }
        userForm.setRoles(selectedRole);
        userService.saveUser(userForm);
        return "redirect:/admin";
    }
}
