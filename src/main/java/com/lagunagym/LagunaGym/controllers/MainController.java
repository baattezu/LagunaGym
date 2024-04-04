package com.lagunagym.LagunaGym.controllers;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping
    public String home(Model model, Authentication authentication) {
        model.addAttribute("title", "Main page");
        model.addAttribute("name",authentication.getName());
        return "home";
    }




}
