package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String printUsers(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("admin", user);
        return "Admin/admin";
    }
    @GetMapping("/new")
    public String newUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", userService.listRoles());
        return "Admin/new";
    }
    @PostMapping("/new")
    public String create(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/update")
    public String Edit(Model model, @PathVariable("id") Long id){
        model.addAttribute("user", userService.get(id));
        model.addAttribute("listRoles", userService.listRoles());
        return "Admin/update";
    }

    @PatchMapping("/{id}")
    public String Update(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/delete")
    public String Delete(@PathVariable("id") Long id){
        userService.delete(id);
        return "redirect:/admin";
    }
}
