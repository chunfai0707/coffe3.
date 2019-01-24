package com.coffe3.mycoffeeshop.controller;

import com.coffe3.mycoffeeshop.domain.Coffee;
import com.coffe3.mycoffeeshop.domain.User;
import com.coffe3.mycoffeeshop.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        model.addAttribute("user", new User());

        return "signup";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") User user) {

        System.out.println(user.getUserName());
        System.out.println(user.getUserEmail());
        System.out.println(user.getUserPassword());

        String[] str = user.getUserPassword().split(",");
        System.out.println(str[0]);
        System.out.println(str[1]);

        return "signup";
    }
}
