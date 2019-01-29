package com.coffe3.mycoffeeshop.repository;

import com.coffe3.mycoffeeshop.domain.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Integer> {
    Coffee findCoffeeByCoffeeId(Integer coffeeId);
}
