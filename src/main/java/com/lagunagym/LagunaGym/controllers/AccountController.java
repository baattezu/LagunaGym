package com.lagunagym.LagunaGym.controllers;


import com.lagunagym.LagunaGym.models.Person;
import com.lagunagym.LagunaGym.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/account")
public class AccountController {

    private PersonService personService;
    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public String getAccount(@PathVariable(value = "id") Long id, Model model){
        Person person = personService.getById(id);
        model.addAttribute("person", person);
        return "account";
    }
    @GetMapping("/register")
    public String regAccForm(Model model){
        Person p = new Person(personService.getLast());
        model.addAttribute("person", p);
        return "register";
    }
    @PostMapping("/register")
    public String addAcc(Person person){
        personService.addPerson(person);
        return "register";
    }


}
