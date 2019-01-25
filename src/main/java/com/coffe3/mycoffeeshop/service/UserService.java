package com.coffe3.mycoffeeshop.service;

import com.coffe3.mycoffeeshop.config.WebMvcConfig;
import com.coffe3.mycoffeeshop.domain.User;
import com.coffe3.mycoffeeshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;
    private final WebMvcConfig webMvcConfig;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public List<String> registerUser(User user) {

        String[] str = user.getUserPassword().split(",");
        List<String> messages = new ArrayList<>();

        if (str[0].equals(str[1])) {

            if (userRepository.getUserByEmail(user.getUserEmail()).isEmpty()) {

                User userToRegister = new User();

                userToRegister.setUserName(user.getUserName());
                userToRegister.setUserEmail(user.getUserEmail());
                userToRegister.setUserPassword(bCryptPasswordEncoder.encode(str[0]));
                userToRegister.setUserCreatedAt(new Date());
                userToRegister.setUserLastUpdated(new Date());

                System.out.println(userToRegister.getUserPassword());

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

    public List<String> validateLogin(User user) {

        List<String> messages = new ArrayList<>();
        List<User> userFromDb = userRepository.getUserByEmail(user.getUserEmail());

        if (userFromDb.isEmpty()) {
            messages.add("Failed");
            messages.add("User Not Found.");
            return messages;

        } else {

            if (bCryptPasswordEncoder.matches(user.getUserPassword(), userFromDb.get(0).getUserPassword())) {
                messages.add("Success");
                return messages;

            } else {
                messages.add("Failed");
                messages.add(("Incorrect Password."));
                return messages;
            }
        }
    }
}
