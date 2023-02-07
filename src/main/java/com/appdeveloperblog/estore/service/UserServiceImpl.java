package com.appdeveloperblog.estore.service;

import com.appdeveloperblog.estore.model.User;
import com.appdeveloperblog.estore.repository.UserRepository;
import com.appdeveloperblog.estore.repository.UserRepositoryImpl;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String firstName, String lastName, String email, String password, String repeatPassword) {
        if (firstName == null || firstName.trim().length() == 0) {
            throw new IllegalArgumentException("User's first name is empty!");
        }

        User user = new User(firstName, lastName, email, password, repeatPassword, UUID.randomUUID().toString());
        boolean isUserCreated = userRepository.save(user);
        if (!isUserCreated) throw new UserServiceException("Could not create user");

        return user;
    }
}
