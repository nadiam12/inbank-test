package com.inbank.loancalculator.service;

import com.inbank.loancalculator.model.User;

import java.util.List;

public interface UserService {

    User getUser(long userId);

    List<User> getAllUsers();
}
