package com.coffe3.mycoffeeshop.repository;

import com.coffe3.mycoffeeshop.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("ALL")
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String role);
}
