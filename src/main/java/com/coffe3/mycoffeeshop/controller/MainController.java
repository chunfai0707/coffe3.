package com.coffe3.mycoffeeshop.controller;

import com.coffe3.mycoffeeshop.config.WebMvcConfig;
import com.coffe3.mycoffeeshop.domain.Coffee;
import com.coffe3.mycoffeeshop.domain.User;
import com.coffe3.mycoffeeshop.repository.CoffeeRepository;
import com.coffe3.mycoffeeshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("ALL")
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainController {

    private final CoffeeRepository coffeeRepository;
    private final UserService userService;
    private final WebMvcConfig webMvcConfig;

    @GetMapping({"/"})
    public String hello(ModelMap model) {

        List<Coffee> list = coffeeRepository.findAll();

        model.addAttribute("coffee", list);
        return "index";
    }

    @GetMapping(value = "/signup")
    public String signUp(ModelMap model,
                         @RequestParam(value = "message", required = false) String message,
                         @RequestParam(value = "errormsg", required = false) String errormsg) {

        model.addAttribute("user", new User());
        model.addAttribute("message", message);
        model.addAttribute("errormsg", errormsg);

        return "signup";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") User user, ModelMap model) {

        String message = userService.registerUser(user).get(0);
        if (message.equalsIgnoreCase("Success")) {
            model.addAttribute("message", message);

        } else {

            String errorMsg = userService.registerUser(user).get(1);
            model.addAttribute("message", message);
            model.addAttribute("errormsg", errorMsg);
        }
        return "signup";

    }
}
