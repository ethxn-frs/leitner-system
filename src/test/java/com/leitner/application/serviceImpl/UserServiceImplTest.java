package com.leitner.application.serviceImpl;

import com.leitner.domain.model.User;
import com.leitner.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl userService;

    private User testUser;
    private Long testId;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();

        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setUsername("testUser");
        testUser.setPassword(passwordEncoder.encode("password"));  // Encode avant de sauvegarder

        testUser = userRepository.save(testUser);
        testId = testUser.getId();
    }

    @Test
    void shouldReturnAllUsers() {
        // When
        List<User> users = userService.getAllUsers();

        // Then
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
    }

    @Test
    void shouldReturnUserById() {
        // When
        User user = userService.getUserById(testId);

        // Then
        assertNotNull(user);
        assertEquals(testId, user.getId());
    }

    @Test
    void shouldCreateUserWithEncodedPassword() {
        // Given
        User newUser = new User();
        newUser.setEmail("new@example.com");
        newUser.setUsername("newUser");
        newUser.setPassword("newPassword");

        // When
        User createdUser = userService.createUser(newUser);

        // Then
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertTrue(passwordEncoder.matches("newPassword", createdUser.getPassword()));
    }

    @Test
    void shouldUpdateUser() {
        // Given
        User updatedUser = new User();
        updatedUser.setEmail("new@example.com");
        updatedUser.setUsername("newUser");
        updatedUser.setPassword("newPassword");

        // When
        User result = userService.updateUser(testId, updatedUser);

        // Then
        assertNotNull(result);
        assertEquals("new@example.com", result.getEmail());
        assertEquals("newUser", result.getUsername());
        assertTrue(passwordEncoder.matches("newPassword", result.getPassword()));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentUser() {
        // When & Then
        assertThrows(RuntimeException.class, () -> userService.updateUser(100L, testUser));
    }

    @Test
    void shouldDeleteUser() {
        // When
        userService.deleteUser(testId);

        // Then
        assertFalse(userRepository.existsById(testId));
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        // When & Then
        assertThrows(RuntimeException.class, () -> userService.deleteUser(3092L));
    }

    @Test
    void shouldAuthenticateValidUser() {
        // When
        String token = userService.authenticate("test@example.com", "password");

        // Then
        assertNotNull(token);
        assertEquals("FAKE-JWT-TOKEN", token);
    }

    @Test
    void shouldThrowExceptionForInvalidAuthentication() {
        // When & Then
        assertThrows(RuntimeException.class, () -> userService.authenticate("test@example.com", "wrongpassword"));
    }
}