package com.example.mycoffeeshop.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Fatal
 */
@Data
@Entity
@Table(name = "coffe3_coffee")
public class Coffee {

    @Id
    @Column(name = "coffe3_id")
    private Integer coffe3Id;
    @Column(name = "coffee_name")
    private String coffeeName;
    @Column(name = "coffee_price")
    private double coffeePrice;
    @Column(name = "coffee_img_url")
    private String coffeeImgUrl;
    @Column(name = "coffee_description")
    private String coffeeDescription;
    @Column(name = "coffee_created_at")
    private Date coffeeCreatedAt;
    @Column(name = "coffee_last_updated")
    private Date coffeeLastUpdated;

}
