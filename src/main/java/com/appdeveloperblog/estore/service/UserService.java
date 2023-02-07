package com.appdeveloperblog.estore.service;

import com.appdeveloperblog.estore.model.User;

public interface UserService {
    User createUser(String firstName, String lastName, String email, String password, String repeatPassword);
}
