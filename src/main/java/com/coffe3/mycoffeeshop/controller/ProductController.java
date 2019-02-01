package com.coffe3.mycoffeeshop.controller;

import com.coffe3.mycoffeeshop.domain.Newsletter;
import com.coffe3.mycoffeeshop.domain.Product;
import com.coffe3.mycoffeeshop.domain.custom.CustomUser;
import com.coffe3.mycoffeeshop.repository.ProductRepository;
import com.coffe3.mycoffeeshop.service.ProductService;
import com.coffe3.mycoffeeshop.tools.CommUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductService productService;
    private final ProductRepository productRepository;

    @GetMapping("/product")
    public String listAllProducts(ModelMap model, HttpSession session) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");
        CommUtils.logUserInfo(logger, currentUser, session);

        List<Product> list = productRepository.findAll();

        session.setAttribute("currentUser", currentUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("products", list);
        model.addAttribute("newsletter", new Newsletter());

        return "coffee";
    }

    @GetMapping("/product/{productType}")
    public String listProductsByType(@PathVariable String productType, ModelMap model, HttpSession session) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");
        CommUtils.logUserInfo(logger, currentUser, session);

        List<Product> list = productRepository.findProductByProductType(productType);

        session.setAttribute("currentUser", currentUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("products", list);
        model.addAttribute("newsletter", new Newsletter());

        return "coffee";
    }

    @RequestMapping(value = "/product/{productType}/{productId}", method = RequestMethod.GET)
    public String getCoffeeById(@PathVariable String productType, @PathVariable Integer productId, ModelMap model, HttpSession session) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");
        CommUtils.logUserInfo(logger, currentUser, session);

        Product product = productRepository.findProductByProductId(productId);
        List<Product> list = productRepository.findProductByProductType(productType);

        List<Product> relatedList = new ArrayList<>();
        relatedList = productService.showRelatedItems(product, list);

        session.setAttribute("currentUser", currentUser);
        model.addAttribute("product", product);
        model.addAttribute("related", relatedList);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("newsletter", new Newsletter());

        return "product";
    }
}
