package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleServiceImp;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.*;



@RequestMapping("/admin")
@Controller
public class AdminController {

    private UserService userService;
    private final RoleServiceImp repositoryService;


    @Autowired
    public AdminController(RoleServiceImp repositoryService, UserService userService) {
        this.userService = userService;
        this.repositoryService = repositoryService;
    }

    @GetMapping()
    public String getUsers(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("admin", users);
        return "admin";
    }

    @GetMapping(value = "/user-form")
    public String userForm(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id != null) {
            User user = userService.getUser(id);
            model.addAttribute("user", user);
        } else {
            User user = new User();
            model.addAttribute("user", user);
        }

        return "user-form";
    }

    @PostMapping(value = "/user-form")

    public String addUserOrUpdate(@ModelAttribute("user") User user
    ) {
        if (user.getId() == null) {
            userService.saveUser(user);
            repositoryService.setUserRoles(user.getId(), user.getRoles());
        } else {
            repositoryService.setUserRoles(user.getId(), user.getRoles());
            userService.updateUser(user);
        }

        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {

        Set<Role> rolesToRemove = new HashSet<>();
        repositoryService.removeRoles(id, rolesToRemove);
        repositoryService.deleteById(id);

        return "redirect:/admin";

    }

}
