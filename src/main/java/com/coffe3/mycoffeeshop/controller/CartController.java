package com.coffe3.mycoffeeshop.controller;

import com.coffe3.mycoffeeshop.domain.Coffee;
import com.coffe3.mycoffeeshop.domain.custom.CustomCartItem;
import com.coffe3.mycoffeeshop.domain.custom.CustomUser;
import com.coffe3.mycoffeeshop.repository.CoffeeRepository;
import com.coffe3.mycoffeeshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CartController {

    private final CoffeeRepository coffeeRepository;
    private final CartService cartService;

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String viewCart(ModelMap model, HttpSession session, @RequestParam(value = "total", required = false) BigDecimal total) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");
        List<CustomCartItem> cartItemList = cartService.renderCart(currentUser.getCoffeeCart());
        total = new BigDecimal(0.00);

        for (CustomCartItem c : cartItemList) {
            total = total.add(c.getTotalPrice());
        }

        session.setAttribute("currentUser", currentUser);

        model.addAttribute("total", total.setScale(2, RoundingMode.HALF_UP));
        model.addAttribute("cart", cartItemList);
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

    @RequestMapping(value = "/clearcart", method = RequestMethod.GET)
    public String clearCart(ModelMap model, HttpSession session) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");

        currentUser.getCoffeeCart().clear();

        session.setAttribute("currentUser", currentUser);
        model.addAttribute("currentUser", currentUser);

        return "redirect:/cart";
    }

    @RequestMapping(value = "updatecart/{item}", method = RequestMethod.GET)
    public String updateCart(@PathVariable List<CustomCartItem> item, ModelMap model, HttpSession session) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");

        System.out.println(item);


        return "redirect:/test";
    }

    @GetMapping("/test")
    public String test(@ModelAttribute("item") List<CustomCartItem> item, ModelMap model, HttpSession session) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");

        session.setAttribute("currentUser", currentUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("item", item);
        return "test";
    }
}
