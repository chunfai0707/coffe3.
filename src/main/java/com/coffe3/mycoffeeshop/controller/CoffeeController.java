package com.coffe3.mycoffeeshop.controller;

import com.coffe3.mycoffeeshop.domain.Coffee;
import com.coffe3.mycoffeeshop.domain.Newsletter;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

        if (!(currentUser == null)) {
            logger.info("---------------User Info--------------------");
            logger.info("Current Session: " + session.getId());
            logger.info("Current User: " + currentUser.getUserName());
            logger.info("Current User Email: " + currentUser.getUserEmail());
            logger.info("--------------------------------------------");
        }

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("coffee", list);
        model.addAttribute("newsletter", new Newsletter());

        return "coffee";
    }

    @RequestMapping(value = "/coffee/{coffeeId}", method = RequestMethod.GET)
    public String getCoffeeById(@PathVariable Integer coffeeId, ModelMap model, HttpSession session) {

        User currentUser = (User) session.getAttribute("currentUser");

        if (!(currentUser == null)) {
            logger.info("---------------User Info--------------------");
            logger.info("Current Session: " + session.getId());
            logger.info("Current User: " + currentUser.getUserName());
            logger.info("Current User Email: " + currentUser.getUserEmail());
            logger.info("--------------------------------------------");
        }

        Coffee coffee = coffeeRepository.findCoffeeByCoffeeId(coffeeId);
        List<Coffee> list = coffeeRepository.findAll();
        List<Coffee> relatedList = new ArrayList<>();

        list.remove(coffee);

        if (list.size() > 4) {
            relatedList = list.stream().sorted(Comparator.comparing(Coffee::getCoffeeLastUpdated).reversed()).collect(Collectors.toList()).subList(0, 4);
        } else {
            relatedList = list.stream().sorted(Comparator.comparing(Coffee::getCoffeeLastUpdated).reversed()).collect(Collectors.toList()).subList(0, list.size());
        }

        model.addAttribute("coffee", coffee);
        model.addAttribute("related", relatedList);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("newsletter", new Newsletter());

        return "product";
    }
}
