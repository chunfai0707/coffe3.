package com.example.mycoffeeshop.tools;

import java.text.DecimalFormat;

public class CommUtils {

    private static DecimalFormat df2 = new DecimalFormat(".##");

    public String formatTwoDecimal(double num) {
        return df2.format(num);
    }
}
