package com.coffe3.mycoffeeshop.controller;

import com.coffe3.mycoffeeshop.domain.Coffee;
import com.coffe3.mycoffeeshop.domain.Newsletter;
import com.coffe3.mycoffeeshop.domain.User;
import com.coffe3.mycoffeeshop.repository.CoffeeRepository;
import com.coffe3.mycoffeeshop.service.NewsletterService;
import com.coffe3.mycoffeeshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CoffeeRepository coffeeRepository;
    private final UserService userService;
    private final NewsletterService newsletterService;

    @GetMapping({"/"})
    public String hello(ModelMap model, HttpSession session) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<Coffee> coffeeList = new ArrayList<>();
        List<User> users = userService.getUserByEmail(auth.getName());
        User currentUser = new User();

        logger.info("Current Session: " + session.getId());

        if (!users.isEmpty()) {

            currentUser = users.get(0);

            logger.info("---------------User Info--------------------");
            logger.info("Current User Name: " + currentUser.getUserName());
            logger.info("Current User Email: " + currentUser.getUserEmail());
            logger.info("Current User Role: " + currentUser.getRoles());
            logger.info("--------------------------------------------");

        }

        session.setAttribute("currentUser", currentUser);

        List<Coffee> list = coffeeRepository.findAll();
        if (list.size() > 8) {
            coffeeList = list.stream().sorted(Comparator.comparing(Coffee::getCoffeeLastUpdated).reversed()).collect(Collectors.toList()).subList(0, 8);
        } else {
            coffeeList = list.stream().sorted(Comparator.comparing(Coffee::getCoffeeLastUpdated).reversed()).collect(Collectors.toList()).subList(0, list.size());
        }

        model.addAttribute("coffee", coffeeList);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("newsletter", new Newsletter());

        return "index";
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public String subscribeNewsletter(@ModelAttribute("newsletter") Newsletter newsletter) {

        newsletterService.subscribeNewsletter(newsletter);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }
}
