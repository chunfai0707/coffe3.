package com.example.mycoffeeshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @GetMapping(value = "/home")
    public String hello(ModelMap model) {
        List<String> list = new ArrayList<>();
        list.add("Mocha");
        list.add("Espresso");
        list.add("Frappucino");
        list.add("Coffee");
        list.add("Latte");
        list.add("Hojicha");
        list.add("Matcha");
        list.add("Sakura");

        model.addAttribute("coffee", list);
        return "index";
    }
}
