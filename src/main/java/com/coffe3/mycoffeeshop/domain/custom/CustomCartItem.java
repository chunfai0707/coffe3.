package com.coffe3.mycoffeeshop.domain.custom;

import com.coffe3.mycoffeeshop.domain.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomCartItem {

    private Product item;
    private BigDecimal itemPrice;
    private Integer quantity;
    private BigDecimal totalPrice;

}
