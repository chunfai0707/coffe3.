package com.coffe3.mycoffeeshop.service;

import com.coffe3.mycoffeeshop.domain.Role;
import com.coffe3.mycoffeeshop.domain.User;
import com.coffe3.mycoffeeshop.repository.RoleRepository;
import com.coffe3.mycoffeeshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public List<String> registerUser(User user) {

        String[] str = user.getUserPassword().split(",");
        List<String> messages = new ArrayList<>();

        if (str[0].equals(str[1])) {
            if (str[0].length() >= 8) {
                if (userRepository.getUserByEmail(user.getUserEmail()).isEmpty()) {

                    User userToRegister = new User();
                    List<Role> roles = new ArrayList<>();

                    roles.add(roleRepository.findByRoleName("user"));

                    userToRegister.setUserName(user.getUserName());
                    userToRegister.setUserEmail(user.getUserEmail());
                    userToRegister.setUserPassword(bCryptPasswordEncoder.encode(str[0]));
                    userToRegister.setUserEnabled(1);
                    userToRegister.setUserCreatedAt(new Date());
                    userToRegister.setUserLastUpdated(new Date());
                    userToRegister.setRoles(roles);

                    userRepository.saveAndFlush(userToRegister);
                    messages.add("Success");

                    logger.info("User {} registered.", userToRegister.getUserEmail());

                    return messages;

                } else {
                    messages.add("Failed");
                    messages.add("Email Already Registered");
                    return messages;
                }
            } else {
                messages.add("Failed");
                messages.add("Password Too Short");
                return messages;
            }

        } else {
            messages.add("Failed");
            messages.add("Passwords Not Match");
            return messages;
        }
    }
}
