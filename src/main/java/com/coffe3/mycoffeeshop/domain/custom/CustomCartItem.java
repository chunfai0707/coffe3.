package com.coffe3.mycoffeeshop.domain.custom;

import com.coffe3.mycoffeeshop.domain.Coffee;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomCartItem {

    private Coffee item;
    private BigDecimal itemPrice;
    private Integer quantity;
    private BigDecimal totalPrice;

}
