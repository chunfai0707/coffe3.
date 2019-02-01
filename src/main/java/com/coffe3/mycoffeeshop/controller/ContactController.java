package com.coffe3.mycoffeeshop.controller;

import com.coffe3.mycoffeeshop.domain.custom.CustomContact;
import com.coffe3.mycoffeeshop.domain.custom.CustomUser;
import com.coffe3.mycoffeeshop.tools.CommUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@SuppressWarnings("ALL")
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContactController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/contact")
    public String contactForm(ModelMap model, HttpSession session) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");
        CommUtils.logUserInfo(logger, currentUser, session);

        session.setAttribute("currentUser", currentUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("contact", new CustomContact());

        return "contact";
    }

    @PostMapping("/sendmessage")
    public String sendMessage(@ModelAttribute(value = "contact") CustomContact contact, ModelMap model, HttpSession session) {

        CustomUser currentUser = (CustomUser) session.getAttribute("currentUser");
        CommUtils.logUserInfo(logger, currentUser, session);

        session.setAttribute("currentUser", currentUser);
        model.addAttribute("currentUser", currentUser);

        return "redirect:/contact";
    }
}
