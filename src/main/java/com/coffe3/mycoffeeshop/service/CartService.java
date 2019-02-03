package com.coffe3.mycoffeeshop.service;

import com.coffe3.mycoffeeshop.domain.Product;
import com.coffe3.mycoffeeshop.domain.custom.CustomCartItem;
import com.coffe3.mycoffeeshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CartService {

    private final ProductRepository productRepository;

    public List<CustomCartItem> renderCart(List<Product> coffeeList) {

        List<CustomCartItem> list = new ArrayList<>();
        Set<Product> unique = new HashSet<>(coffeeList);

        for (Product c : unique) {

            CustomCartItem customCartItem = new CustomCartItem();

            customCartItem.setItem(c);
            customCartItem.setItemPrice(new BigDecimal(c.getProductPrice()).setScale(2, RoundingMode.HALF_UP));
            customCartItem.setQuantity(Collections.frequency(coffeeList, c));
            customCartItem.setTotalPrice(calculateTotalPrice(new BigDecimal(c.getProductPrice()), Collections.frequency(coffeeList, c)));

            list.add(customCartItem);
        }

        return list;
    }

    public List<Product> updateCart(String itemId, String amount) {

        List<String> coffeeIds = Arrays.asList(itemId.split(","));
        List<String> quantity = Arrays.asList(amount.split(","));
        List<Product> updatedList = new ArrayList<>();

        for (int i = 0; i < coffeeIds.size(); i++) {
            for (int j = 0; j < Integer.parseInt(quantity.get(i)); j++) {
                Product updateItem = productRepository.findProductByProductId(Integer.parseInt(coffeeIds.get(i)));
                updatedList.add(updateItem);
            }
        }

        return updatedList;
    }

    private BigDecimal calculateTotalPrice(BigDecimal itemPrice, Integer quantity) {
        return itemPrice.multiply(new BigDecimal(quantity)).setScale(2, RoundingMode.HALF_UP);
    }
}
