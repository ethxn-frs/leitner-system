package com.leitner.application.serviceImpl;

import com.leitner.domain.model.User;
import com.leitner.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setEmail("test@example.com");
        testUser.setUsername("testUser");
        testUser.setPassword("password");
    }

    @Test
    void shouldCreateUserWithEncodedPassword() {
        // Given
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        User createdUser = userService.createUser(testUser);

        // Then
        assertNotNull(createdUser);
        assertEquals("hashedPassword", createdUser.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldReturnAllUsers() {
        // Given
        when(userRepository.findAll()).thenReturn(List.of(testUser));

        // When
        List<User> users = userService.getAllUsers();

        // Then
        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnUserById() {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));

        // When
        Optional<User> user = userService.getUserById(1);

        // Then
        assertTrue(user.isPresent());
        assertEquals(testUser.getId(), user.get().getId());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void shouldUpdateUser() {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.encode(anyString())).thenReturn("newHashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User updatedUser = new User();
        updatedUser.setEmail("new@example.com");
        updatedUser.setUsername("newUser");
        updatedUser.setPassword("newPassword");

        // When
        User result = userService.updateUser(1, updatedUser);

        // Then
        assertNotNull(result);
        assertEquals("new@example.com", result.getEmail());
        assertEquals("newUser", result.getUsername());
        assertEquals("newHashedPassword", result.getPassword());
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentUser() {
        // Given
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> userService.updateUser(1, testUser));
    }

    @Test
    void shouldDeleteUser() {
        // Given
        doNothing().when(userRepository).deleteById(1);

        // When
        userService.deleteUser(1);

        // Then
        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void shouldAuthenticateValidUser() {
        // Given
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password", testUser.getPassword())).thenReturn(true);

        // When
        String token = userService.authenticate("test@example.com", "password");

        // Then
        assertNotNull(token);
        assertEquals("FAKE-JWT-TOKEN", token);
    }

    @Test
    void shouldThrowExceptionForInvalidAuthentication() {
        // Given
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("wrongpassword", testUser.getPassword())).thenReturn(false);

        // When & Then
        assertThrows(RuntimeException.class, () -> userService.authenticate("test@example.com", "wrongpassword"));
    }
}
