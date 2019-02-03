package com.coffe3.mycoffeeshop.repository;

import com.coffe3.mycoffeeshop.domain.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsletterRepository extends JpaRepository<Newsletter, Integer> {
}
