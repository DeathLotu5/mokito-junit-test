package com.appdeveloperblog.estore.service;

import com.appdeveloperblog.estore.model.User;
import com.appdeveloperblog.estore.repository.UserRepository;
import com.appdeveloperblog.estore.repository.UserRepositoryImpl;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailVerificationSerive emailVerificationSerive;

    public UserServiceImpl(UserRepository userRepository, EmailVerificationSerive emailVerificationSerive) {
        this.userRepository = userRepository;
        this.emailVerificationSerive = emailVerificationSerive;
    }

    @Override
    public User createUser(String firstName, String lastName, String email, String password, String repeatPassword) {
        if (firstName == null || firstName.trim().length() == 0) {
            throw new IllegalArgumentException("User's first name is empty!");
        }

        User user = new User(firstName, lastName, email, password, repeatPassword, UUID.randomUUID().toString());
        boolean isUserCreated;
        try {
            isUserCreated = userRepository.save(user);
        } catch (RuntimeException ex) {
            throw new UserServiceException(ex.getMessage());
        }
        if (!isUserCreated) throw new UserServiceException("Could not create user");

        try {
            emailVerificationSerive.scheduleEmailConfirmation(user);
        } catch (RuntimeException ex) {
            throw new EmailVerificationException(ex.getMessage());
        }
        return user;
    }

    public void test() {
        System.out.println("Demo method");
    }
}
