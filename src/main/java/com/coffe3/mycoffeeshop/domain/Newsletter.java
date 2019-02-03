/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coffe3.mycoffeeshop.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Fatal
 */
@Data
@Entity
@Table(name = "coffe3_newsletter")

public class Newsletter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "newsletter_id")
    private Integer newsletterId;
    @Column(name = "newsletter_email")
    private String newsletterEmail;
    @Column(name = "newsletter_created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date newsletterCreatedAt;
    @Column(name = "newsletter_last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date newsletterLastUpdated;

}
