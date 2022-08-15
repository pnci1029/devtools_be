package com.example.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin("http://localhost:3000")
@Controller
public class HomeController {

    @GetMapping("/")
    public String getget(Model model) {
        String loginUser = "테스트이름";
        model.addAttribute("loginUser", loginUser);
        return "index";
    }

    @GetMapping("/login")
    public String adad() {
        return "login";
    }

    @GetMapping("/signup")
    public String adddad() {
        return "signup";
    }

    @PostMapping("/signup")
    public String adada() {
        return "redirect:/login.html";
    }

    @GetMapping("/hello")
    public String adsdas(Model model) {
        String testName = "하하호호";
        model.addAttribute("testest", testName);
        return "testest";
    }


}
