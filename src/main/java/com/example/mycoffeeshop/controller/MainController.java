package com.example.mycoffeeshop.controller;

import com.example.mycoffeeshop.domain.Coffee;
import com.example.mycoffeeshop.repository.CoffeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@SuppressWarnings("ALL")
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class MainController {

    private final CoffeeRepository coffeeRepository;

    @GetMapping(value = "/home")
    public String hello(ModelMap model) {

        List<Coffee> list = coffeeRepository.findAll();

        model.addAttribute("coffee", list);
        return "index";
    }
}
