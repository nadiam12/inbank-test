package com.inbank.loancalculator.service;

import com.inbank.loancalculator.error.UserNotFoundException;
import com.inbank.loancalculator.model.User;
import com.inbank.loancalculator.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceProvider implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceProvider.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            logger.error("User with id=" + userId + " not found");
            throw new UserNotFoundException();
        }
        return user.get();
    }

    @Override
    public List<User> getAllUsers() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}
