package com.coffe3.mycoffeeshop.domain.custom;

import lombok.Data;

@Data
public class CustomContact {

    private String firstName;
    private String lastName;
    private String subject;
    private String message;
}
