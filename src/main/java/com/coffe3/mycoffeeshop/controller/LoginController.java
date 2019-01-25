package com.coffe3.mycoffeeshop.controller;

import com.coffe3.mycoffeeshop.config.WebMvcConfig;
import com.coffe3.mycoffeeshop.domain.User;
import com.coffe3.mycoffeeshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

    private final WebMvcConfig webMvcConfig;
    private final UserService userService;

    @GetMapping(value = "/login")
    public String login(ModelMap model,
                        @RequestParam(value = "message", required = false) String message,
                        @RequestParam(value = "errormsg", required = false) String errorMsg) {


        model.addAttribute("user", new User());
        model.addAttribute("message", message);
        model.addAttribute("errormsg", errorMsg);

        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String validateLogin(@ModelAttribute("user") User user, ModelMap model) {

        String message = userService.validateLogin(user).get(0);

        if (message.equalsIgnoreCase("Success")) {
            model.addAttribute("message", message);

        } else {

            String errorMsg = userService.validateLogin(user).get(1);
            model.addAttribute("message", message);
            model.addAttribute("errorMsg", errorMsg);
        }
        return "login";
    }

}
