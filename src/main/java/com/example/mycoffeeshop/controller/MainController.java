package com.example.mycoffeeshop.controller;

import com.example.mycoffeeshop.domain.Coffee;
import com.example.mycoffeeshop.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@SuppressWarnings("ALL")
@Controller
public class MainController {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @GetMapping({"/"})
    public String hello(ModelMap model) {

        List<Coffee> list = coffeeRepository.findAll();

        model.addAttribute("coffee", list);
        return "index";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/signup")
    public String signUp(ModelMap model) {
        return "signup";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register() {
        ModelAndView model = new ModelAndView();
        return model;
    }
}
