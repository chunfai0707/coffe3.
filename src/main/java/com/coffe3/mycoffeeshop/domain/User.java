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
import java.util.List;

/**
 * @author Fatal
 */
@Data
@Entity
@Table(name = "coffe3_user")

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "user_enabled")
    private Integer userEnabled;
    @Column(name = "user_created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date userCreatedAt;
    @Column(name = "user_last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date userLastUpdated;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "coffe3_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
}
