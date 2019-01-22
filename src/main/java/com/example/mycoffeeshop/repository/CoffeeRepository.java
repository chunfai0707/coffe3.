package com.example.mycoffeeshop.repository;

import com.example.mycoffeeshop.domain.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Integer> {
}
