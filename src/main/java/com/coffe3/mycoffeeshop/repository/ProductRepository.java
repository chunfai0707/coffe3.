package com.coffe3.mycoffeeshop.repository;

import com.coffe3.mycoffeeshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findProductByProductId(Integer productId);
}
