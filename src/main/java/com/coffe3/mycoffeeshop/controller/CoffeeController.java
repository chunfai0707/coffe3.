package com.coffe3.mycoffeeshop.controller;

import com.coffe3.mycoffeeshop.domain.Coffee;
import com.coffe3.mycoffeeshop.repository.CoffeeRepository;
import com.coffe3.mycoffeeshop.service.CoffeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@SuppressWarnings("ALL")
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CoffeeController {

    private final CoffeeService coffeeService;
    private final CoffeeRepository coffeeRepository;

    @GetMapping({"/coffee"})
    public String listAllCoffee(ModelMap model, HttpSession session) {

        List<String> userName = (List<String>) session.getAttribute("userName");
        List<Coffee> list = coffeeRepository.findAll();

        model.addAttribute("userName", userName);
        model.addAttribute("coffee", list);

        return "coffee";
    }

}
