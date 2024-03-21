package com.lagunagym.LagunaGym.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/{name}")
    public String home(Model model, @PathVariable(value = "name",required = false) String name) {
        model.addAttribute("title", "Main page");
        model.addAttribute("name",name);
        return "home";
    }




}
