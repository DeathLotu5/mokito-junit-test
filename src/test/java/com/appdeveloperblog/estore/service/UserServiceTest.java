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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    EmailVerificationSeriveImpl emailVerificationSerive;

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
        Mockito.verify(userRepository).save(Mockito.any(User.class));
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

    @Test
    @DisplayName("If save() method causes RuntimeException, A UserServiceException is thrown")
    void testCreateUser_whenSaveMethodThrowsException_thenThrowsUserServiceException() {
        // Arrange
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenThrow(RuntimeException.class);

        // Act & Assert
        Assertions.assertThrows(
                UserServiceException.class,
                () -> {
                    userService.createUser(firstName, lastName, email, password, repeatPassword);
                },
                "Should have thrown UserServiceException instead!"
                );
    }

    @Test
    @DisplayName("EmailVerificationException is handled")
    void testCreateUser_whenEmailNotificationThrowsRuntimeException_thenThrowsUserServiceException() {
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(true);
        Mockito.doThrow(EmailVerificationException.class)
                .when(emailVerificationSerive)
                .scheduleEmailConfirmation(Mockito.any(User.class));
//        Mockito.doNothing().when(emailVerificationSerive).scheduleEmailConfirmation(Mockito.any(User.class));

        // Act & Assert
        Assertions.assertThrows(
                EmailVerificationException.class,
                () -> {
                    userService.createUser(firstName, lastName, email, password, repeatPassword);
                },
                "Should have thrown UserServiceException instead!"
        );

        // Assert
        verify(emailVerificationSerive).scheduleEmailConfirmation(any(User.class));
    }

    @Test
    @DisplayName("Schedule Email Confirmation is executed")
    void testCreateUser_whenCreatedUser_shedulesEmailConfirmation() {
        // Arrange
        when(userRepository.save(any(User.class))).thenReturn(true);

        doCallRealMethod().when(emailVerificationSerive)
                .scheduleEmailConfirmation(any(User.class));

        // Act
        userService.createUser(firstName, lastName, email, password, repeatPassword);

        // Assert
        verify(emailVerificationSerive).scheduleEmailConfirmation(any(User.class));

    }
}
