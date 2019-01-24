package com.coffe3.mycoffeeshop.repository;

import com.coffe3.mycoffeeshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
