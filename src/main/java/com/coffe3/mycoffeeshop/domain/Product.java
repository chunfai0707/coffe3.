package com.coffe3.mycoffeeshop.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Fatal
 */
@Data
@Entity
@Table(name = "coffe3_product")
public class Product implements Serializable {

    @Id
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_price")
    private double productPrice;
    @Column(name = "product_img_url")
    private String productImgUrl;
    @Column(name = "product_description")
    private String productDescription;
    @Column(name = "product_type")
    private String productType;
    @Column(name = "product_available")
    private Integer productAvailable;
    @Column(name = "product_created_at")
    private Date productCreatedAt;
    @Column(name = "product_last_updated")
    private Date productLastUpdated;

}
