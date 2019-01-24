/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coffe3.mycoffeeshop.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Fatal
 */
@Data
@Entity
@Table(name = "coffe3_user")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "user_name")
    private String userName;
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "user_email")
    private String userEmail;
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "user_password")
    private String userPassword;
    @NotNull
    @Column(name = "user_active")
    private int userActive;
    @NotNull
    @Column(name = "user_created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date userCreatedAt;
    @NotNull
    @Column(name = "user_last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date userLastUpdated;

}
