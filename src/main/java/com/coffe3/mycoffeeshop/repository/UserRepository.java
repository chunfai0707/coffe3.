package com.coffe3.mycoffeeshop.repository;

import com.coffe3.mycoffeeshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT x FROM User x WHERE x.userEmail=:email")
    List<User> getUserByEmail(@Param("email") String email);

}
