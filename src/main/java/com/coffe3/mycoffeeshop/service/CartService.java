package com.coffe3.mycoffeeshop.service;

import com.coffe3.mycoffeeshop.domain.Coffee;
import com.coffe3.mycoffeeshop.domain.custom.CustomCartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CartService {

    public List<CustomCartItem> renderCart(List<Coffee> coffeeList) {

        List<CustomCartItem> list = new ArrayList<>();
        Set<Coffee> unique = new HashSet<>(coffeeList);

        for (Coffee c : unique) {

            CustomCartItem customCartItem = new CustomCartItem();

            customCartItem.setItem(c);
            customCartItem.setItemPrice(new BigDecimal(c.getCoffeePrice()).setScale(2, RoundingMode.HALF_UP));
            customCartItem.setQuantity(Collections.frequency(coffeeList, c));
            customCartItem.setTotalPrice(calculateTotalPrice(new BigDecimal(c.getCoffeePrice()), Collections.frequency(coffeeList, c)));

            list.add(customCartItem);
        }

        return list;
    }

    public List<Coffee> updateCart(List<CustomCartItem> cartItemList) {

        List<Coffee> updatedList = new ArrayList<>();
        for (CustomCartItem c : cartItemList) {
            for (int i = 0; i < c.getQuantity(); i++) {
                updatedList.add(c.getItem());
            }
        }

        return updatedList;
    }


    private BigDecimal calculateTotalPrice(BigDecimal itemPrice, Integer quantity) {
        return itemPrice.multiply(new BigDecimal(quantity)).setScale(2, RoundingMode.HALF_UP);
    }
}
