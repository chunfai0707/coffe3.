package com.coffe3.mycoffeeshop.domain.custom;

import com.coffe3.mycoffeeshop.domain.Product;
import com.coffe3.mycoffeeshop.domain.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CustomUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String userName;
    private String userEmail;
    private List<Role> roles;
    private List<Product> coffeeCart;

}
