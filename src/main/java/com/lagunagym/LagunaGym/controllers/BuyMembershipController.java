package com.lagunagym.LagunaGym.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BuyMembershipController {

    @GetMapping("/buymembership")
    public String buymembership(Model model) {
        model.addAttribute("title", "Buy Membership");
        return "buy-membership";
    }

}
