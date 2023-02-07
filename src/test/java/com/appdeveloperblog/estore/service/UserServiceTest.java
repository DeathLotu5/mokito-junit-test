package com.appdeveloperblog.estore.service;

import com.appdeveloperblog.estore.model.User;
import com.appdeveloperblog.estore.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;
    String firstName;
    String lastName;
    String email;
    String password;
    String repeatPassword;

    @BeforeEach
    void init() {
        firstName = "Duong";
        lastName = "Pham Hoang";
        email = "duongph.4a@gmail.com";
        password = "Kutit@130599";
        repeatPassword = "Kutit@130599";
    }

    @Test
    @DisplayName("User object created")
    void testCreateUser_whenUserDetailsProvided_returnUserObject() {
        //Arrange
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(true);

        //Act
        User user = userService.createUser(firstName, lastName, email, password, repeatPassword);

        //Assert
        Assertions.assertNotNull(user, "The createUser() should not have returned null");
        Assertions.assertEquals(firstName, user.getFirstName(), "User's first name is incorrect!");
        Assertions.assertEquals(lastName, user.getLastName(), "User's last name is incorrect!");
        Assertions.assertEquals(email, user.getEmail(), "User's email is incorrect!");
        Assertions.assertNotNull(user.getId(), "User id is missing");
    }

    @Test
    @DisplayName("Empty first name cases correct exception")
    void testCreatedUser_whenFirstnameIsEmpty_throwsIllegalException() {
        firstName = "";
        String expectedResult = "User's first name is empty!";

        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Empty firstname should have caused an Illegal Argument Exception");

        Assertions.assertEquals(expectedResult,
                illegalArgumentException.getMessage(),
                "Exception error message is not correct");
    }


}
