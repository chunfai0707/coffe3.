package com.coffe3.mycoffeeshop.controller;

import com.coffe3.mycoffeeshop.domain.Newsletter;
import com.coffe3.mycoffeeshop.domain.Product;
import com.coffe3.mycoffeeshop.domain.User;
import com.coffe3.mycoffeeshop.domain.custom.CustomUser;
import com.coffe3.mycoffeeshop.repository.ProductRepository;
import com.coffe3.mycoffeeshop.service.NewsletterService;
import com.coffe3.mycoffeeshop.service.ProductService;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final UserService userService;
    private final NewsletterService newsletterService;

    @GetMapping({"/"})
    public String hello(ModelMap model, HttpSession session) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<Product> latestList = new ArrayList<>();
        List<User> users = userService.getUserByEmail(auth.getName());

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser") == null ? new CustomUser() : (CustomUser) session.getAttribute("currentUser");

        List<Product> cart = currentUser.getCoffeeCart() == null ? new ArrayList<>() : currentUser.getCoffeeCart();
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

        List<Product> list = productRepository.findAll();
        latestList = productService.showLatestItem(list);

        model.addAttribute("products", latestList);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("newsletter", new Newsletter());

        return "index";
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public String subscribeNewsletter(@ModelAttribute("newsletter") Newsletter newsletter) {
        newsletterService.subscribeNewsletter(newsletter);
        return "redirect:/";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@RequestParam(value = "search") String search, ModelMap model, HttpSession session) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");
        CommUtils.logUserInfo(logger, currentUser, session);

        List<Product> list = productRepository.findByProductNameContainingIgnoreCase(search);


        session.setAttribute("currentUser", currentUser);

        model.addAttribute("type", "search");
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("products", list);
        model.addAttribute("newsletter", new Newsletter());

        return "coffee";
    }


    @GetMapping("/logout")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }
}
