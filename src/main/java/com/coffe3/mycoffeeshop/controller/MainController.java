package com.coffe3.mycoffeeshop.controller;

import com.coffe3.mycoffeeshop.config.WebMvcConfig;
import com.coffe3.mycoffeeshop.domain.Coffee;
import com.coffe3.mycoffeeshop.domain.Newsletter;
import com.coffe3.mycoffeeshop.repository.CoffeeRepository;
import com.coffe3.mycoffeeshop.service.NewsletterService;
import com.coffe3.mycoffeeshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainController {

    private final CoffeeRepository coffeeRepository;
    private final UserService userService;
    private final WebMvcConfig webMvcConfig;
    private final NewsletterService newsletterService;

    @GetMapping({"/"})
    public String hello(ModelMap model, HttpSession session) {

        List<String> userName = (List<String>) session.getAttribute("userName");
        System.out.println(session.getId());

        if (userName == null) {
            userName = new ArrayList<>();

        } else {
            System.out.println(userName);
        }

        List<Coffee> list = coffeeRepository.findAll();

        model.addAttribute("coffee", list);
        model.addAttribute("userName", userName);
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
