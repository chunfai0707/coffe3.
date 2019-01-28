package com.coffe3.mycoffeeshop.controller;

import com.coffe3.mycoffeeshop.domain.Coffee;
import com.coffe3.mycoffeeshop.domain.User;
import com.coffe3.mycoffeeshop.repository.CoffeeRepository;
import com.coffe3.mycoffeeshop.service.CoffeeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CoffeeService coffeeService;
    private final CoffeeRepository coffeeRepository;

    @GetMapping({"/coffee"})
    public String listAllCoffee(ModelMap model, HttpSession session) {

        List<Coffee> list = coffeeRepository.findAll();
        User currentUser = (User) session.getAttribute("currentUser");

        logger.info("---------------User Info--------------------");
        logger.info("Current Session: " + session.getId());
        logger.info("Current User: " + currentUser.getUserName());
        logger.info("Current User Email: " + currentUser.getUserEmail());
        logger.info("--------------------------------------------");

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("coffee", list);

        return "coffee";
    }

}
