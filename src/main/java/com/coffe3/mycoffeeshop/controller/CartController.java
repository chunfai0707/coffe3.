package com.coffe3.mycoffeeshop.controller;

import com.coffe3.mycoffeeshop.domain.Product;
import com.coffe3.mycoffeeshop.domain.custom.CustomCartItem;
import com.coffe3.mycoffeeshop.domain.custom.CustomUser;
import com.coffe3.mycoffeeshop.repository.ProductRepository;
import com.coffe3.mycoffeeshop.service.CartService;
import com.coffe3.mycoffeeshop.tools.CommUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@SuppressWarnings("unused")
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CartController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductRepository productRepository;
    private final CartService cartService;

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String viewCart(ModelMap model, HttpSession session) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");
        CommUtils.logUserInfo(logger, currentUser, session);

        BigDecimal total = new BigDecimal(0.00);
        List<CustomCartItem> cartItemList = cartService.renderCart(currentUser.getCoffeeCart());

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
    public String addToCart(@RequestParam(value = "coffee") Integer productId, @RequestParam(value = "amount") Integer amount, ModelMap model, HttpSession session) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");
        CommUtils.logUserInfo(logger, currentUser, session);

        Product coffee = productRepository.findProductByProductId(productId);

        for (int i = 0; i < amount; i++) {
            currentUser.getCoffeeCart().add(coffee);
        }

        session.setAttribute("currentUser", currentUser);
        model.addAttribute("coffee", productId);
        model.addAttribute("amount", amount);
        model.addAttribute("currentUser", currentUser);

        return "redirect:/cart";

    }

    @RequestMapping(value = "/clearcart", method = RequestMethod.GET)
    public String clearCart(ModelMap model, HttpSession session) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");
        CommUtils.logUserInfo(logger, currentUser, session);

        currentUser.getCoffeeCart().clear();

        session.setAttribute("currentUser", currentUser);
        model.addAttribute("currentUser", currentUser);

        return "redirect:/cart";
    }

    @RequestMapping(value = "/updatecart", method = RequestMethod.POST)
    public String updateCart(@RequestParam(value = "itemId", required = false) String itemId,
                             @RequestParam(value = "quantity", required = false) String amount,
                             ModelMap model, HttpSession session) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");
        CommUtils.logUserInfo(logger, currentUser, session);

        if (itemId == null || amount == null) {
            session.setAttribute("currentUser", currentUser);
            model.addAttribute("currentUser", currentUser);
            return "redirect:/cart";

        } else {
            currentUser.setCoffeeCart(cartService.updateCart(itemId, amount));
        }

        session.setAttribute("currentUser", currentUser);
        model.addAttribute("currentUser", currentUser);

        return "redirect:/cart";
    }

    @RequestMapping(value = "/deletefromcart/{productId}", method = RequestMethod.GET)
    public String getCoffeeById(@PathVariable Integer productId, ModelMap model, HttpSession session) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");
        CommUtils.logUserInfo(logger, currentUser, session);

        currentUser.getCoffeeCart().removeIf(x -> x.equals(productRepository.findProductByProductId(productId)));

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
