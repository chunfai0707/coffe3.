package com.coffe3.mycoffeeshop.service;

import com.coffe3.mycoffeeshop.config.WebMvcConfig;
import com.coffe3.mycoffeeshop.domain.User;
import com.coffe3.mycoffeeshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;
    private final WebMvcConfig webMvcConfig;


    public List<String> registerUser(User user) {

        String[] str = user.getUserPassword().split(",");
        List<String> messages = new ArrayList<>();

        if (str[0].equals(str[1])) {

            if (userRepository.getUserByEmail(user.getUserEmail()).isEmpty()) {

                User userToRegister = new User();

                userToRegister.setUserName(user.getUserName());
                userToRegister.setUserEmail(user.getUserEmail());
                userToRegister.setUserPassword(webMvcConfig.passwordEncoder().encode(user.getUserPassword()));
                userToRegister.setUserCreatedAt(new Date());
                userToRegister.setUserLastUpdated(new Date());

                userRepository.saveAndFlush(userToRegister);
                messages.add("Success");
                return messages;

            } else {
                messages.add("Failed");
                messages.add("Email Already Registered");
                return messages;
            }
        } else {
            messages.add("Failed");
            messages.add("Passwords Not Match");
            return messages;
        }
    }
}
