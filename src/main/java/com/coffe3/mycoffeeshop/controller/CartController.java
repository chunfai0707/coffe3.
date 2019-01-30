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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

        model.addAttribute("custom", new CustomCartItem());
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

    @RequestMapping(value = "/updatecart", method = RequestMethod.POST)
    public String updateCart(@RequestParam(value = "itemId") String itemId, @RequestParam(value = "quantity") String amount, ModelMap model, HttpSession session) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");

        currentUser.setCoffeeCart(cartService.updateCart(itemId, amount));
        session.setAttribute("currentUser", currentUser);

        model.addAttribute("currentUser", currentUser);

        return "redirect:/cart";
    }

    @PostMapping("/test")
    public String test(@RequestParam(value = "itemName") String itemId, @RequestParam(value = "quantity") String amount, ModelMap model, HttpSession session) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");

        currentUser.setCoffeeCart(cartService.updateCart(itemId, amount));


        model.addAttribute("currentUser", currentUser);
        model.addAttribute("amount", amount);
        return "test";
    }
}
