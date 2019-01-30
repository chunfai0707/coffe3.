package com.coffe3.mycoffeeshop.controller;

import com.coffe3.mycoffeeshop.domain.Coffee;
import com.coffe3.mycoffeeshop.domain.Newsletter;
import com.coffe3.mycoffeeshop.domain.custom.CustomUser;
import com.coffe3.mycoffeeshop.repository.CoffeeRepository;
import com.coffe3.mycoffeeshop.service.CoffeeService;
import com.coffe3.mycoffeeshop.tools.CommUtils;
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

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");
        CommUtils.logUserInfo(logger, currentUser, session);

        List<Coffee> list = coffeeRepository.findAll();

        session.setAttribute("currentUser", currentUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("coffee", list);
        model.addAttribute("newsletter", new Newsletter());

        return "coffee";
    }

    @RequestMapping(value = "/coffee/{coffeeId}", method = RequestMethod.GET)
    public String getCoffeeById(@PathVariable Integer coffeeId, ModelMap model, HttpSession session) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");
        CommUtils.logUserInfo(logger, currentUser, session);

        Coffee coffee = coffeeRepository.findCoffeeByCoffeeId(coffeeId);
        List<Coffee> list = coffeeRepository.findAll();

        List<Coffee> relatedList = new ArrayList<>();
        relatedList = coffeeService.showRelatedItems(coffee, list);

        session.setAttribute("currentUser", currentUser);
        model.addAttribute("coffee", coffee);
        model.addAttribute("related", relatedList);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("newsletter", new Newsletter());

        return "product";
    }
}
