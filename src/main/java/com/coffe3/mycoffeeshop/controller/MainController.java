package com.coffe3.mycoffeeshop.controller;

import com.coffe3.mycoffeeshop.domain.Coffee;
import com.coffe3.mycoffeeshop.domain.Newsletter;
import com.coffe3.mycoffeeshop.domain.User;
import com.coffe3.mycoffeeshop.domain.custom.CustomUser;
import com.coffe3.mycoffeeshop.repository.CoffeeRepository;
import com.coffe3.mycoffeeshop.service.CoffeeService;
import com.coffe3.mycoffeeshop.service.NewsletterService;
import com.coffe3.mycoffeeshop.service.UserService;
import com.coffe3.mycoffeeshop.tools.CommUtils;
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
import java.util.List;

@SuppressWarnings("ALL")
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CoffeeRepository coffeeRepository;
    private final CoffeeService coffeeService;
    private final UserService userService;
    private final NewsletterService newsletterService;

    @GetMapping({"/"})
    public String hello(ModelMap model, HttpSession session) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<Coffee> latestList = new ArrayList<>();
        List<User> users = userService.getUserByEmail(auth.getName());

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser") == null ? new CustomUser() : (CustomUser) session.getAttribute("currentUser");

        List<Coffee> cart = currentUser.getCoffeeCart() == null ? new ArrayList<>() : currentUser.getCoffeeCart();
        currentUser.setCoffeeCart(cart);

        CommUtils.logUserInfo(logger, currentUser, session);

        if (!users.isEmpty()) {

            currentUser.setUserId(users.get(0).getUserId());
            currentUser.setUserName(users.get(0).getUserName());
            currentUser.setUserEmail(users.get(0).getUserEmail());
            currentUser.setRoles(users.get(0).getRoles());
            currentUser.setCoffeeCart(cart);

            CommUtils.logUserInfo(logger, currentUser, session);
        }

        session.setAttribute("currentUser", currentUser);

        List<Coffee> list = coffeeRepository.findAll();
        latestList = coffeeService.showLatestItem(list);

        model.addAttribute("coffee", latestList);
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
