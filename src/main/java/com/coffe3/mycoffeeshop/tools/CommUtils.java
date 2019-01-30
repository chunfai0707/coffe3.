package com.coffe3.mycoffeeshop.tools;

import com.coffe3.mycoffeeshop.domain.custom.CustomUser;
import org.slf4j.Logger;

import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;

public class CommUtils {

    private static DecimalFormat df2 = new DecimalFormat(".##");

    public static String formatTwoDecimal(double num) {
        return df2.format(num);
    }

    public static void logUserInfo(Logger logger, CustomUser currentUser, HttpSession session) {

        logger.info("---------------User Info--------------------");
        logger.info("Current Session ID: " + session.getId());
        logger.info("Current User Name: " + currentUser.getUserName());
        logger.info("Current User Email: " + currentUser.getUserEmail());
        logger.info("Current User Role: " + currentUser.getRoles());
        logger.info("Current Cart: " + currentUser.getCoffeeCart().size());
        logger.info("--------------------------------------------");

    }
}
