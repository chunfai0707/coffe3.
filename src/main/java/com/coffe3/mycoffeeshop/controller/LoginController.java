package com.coffe3.mycoffeeshop.controller;

import com.coffe3.mycoffeeshop.domain.User;
import com.coffe3.mycoffeeshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@SuppressWarnings("ALL")
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

    private final UserService userService;

    @GetMapping(value = "/signup")
    public String userRegister(ModelMap model,
                               @RequestParam(value = "message", required = false) String message,
                               @RequestParam(value = "errormsg", required = false) String errormsg) {

        model.addAttribute("user", new User());
        model.addAttribute("message", message);
        model.addAttribute("errormsg", errormsg);

        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String userRegisterValidation(@ModelAttribute("user") User user, ModelMap model) {

        String message = userService.registerUser(user).get(0);
        if (message.equalsIgnoreCase("Success")) {
            model.addAttribute("message", message);

        } else {

            String errorMsg = userService.registerUser(user).get(1);
            model.addAttribute("message", message);
            model.addAttribute("errorMsg", errorMsg);
        }
        return "signup";
    }

    @GetMapping(value = "/login")
    public String login(ModelMap model,
                        @RequestParam(value = "message", required = false) String message,
                        @RequestParam(value = "errorMsg", required = false) String errorMsg) {

        model.addAttribute("user", new User());
        model.addAttribute("message", message);
        model.addAttribute("errorMsg", errorMsg);

        return "login";
    }

    @RequestMapping(value = {"/accessdenied"}, method = RequestMethod.GET)
    public ModelAndView accessDenied() {
        ModelAndView model = new ModelAndView();

        model.addObject("user", new User());
        model.addObject("errorMsg", "Wrong Email or Password.");
        model.addObject("message", "Failed");

        model.setViewName("/login");

        return model;
    }
}
