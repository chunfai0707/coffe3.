package com.coffe3.mycoffeeshop.controller;

import com.coffe3.mycoffeeshop.domain.Coffee;
import com.coffe3.mycoffeeshop.domain.custom.CustomUser;
import com.coffe3.mycoffeeshop.repository.CoffeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CartController {

    private final CoffeeRepository coffeeRepository;

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String viewCart(ModelMap model, HttpSession session) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");

        session.setAttribute("currentUser", currentUser);
        model.addAttribute("cart", currentUser.getCoffeeCart());
        model.addAttribute("currentUser", currentUser);

        return "cart";
    }

    @RequestMapping(value = "/addtocart", method = RequestMethod.GET)
    public String addToCart(@RequestParam(value = "coffee") Integer coffeeId, @RequestParam(value = "amount") Integer amount, ModelMap model, HttpSession session) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");
        Coffee coffee = coffeeRepository.findCoffeeByCoffeeId(coffeeId);

        for (int i = 0; i < amount; i++) {
            currentUser.getCoffeeCart().add(coffee);
        }

        session.setAttribute("currentUser", currentUser);
        model.addAttribute("coffee", coffeeId);
        model.addAttribute("amount", amount);
        model.addAttribute("currentUser", currentUser);

        return "redirect:/coffee";

    }

    @GetMapping("/test")
    public String test(@ModelAttribute("coffee") Integer coffeeId, @ModelAttribute("amount") Integer amount, ModelMap model, HttpSession session) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");

        session.setAttribute("currentUser", currentUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("coffee", coffeeId);
        model.addAttribute("amount", amount);
        return "test";
    }
}
