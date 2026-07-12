package com.example.LibraryManager.services;

import com.example.LibraryManager.dtos.requests.UserCreateRequest;
import com.example.LibraryManager.entities.User;
import com.example.LibraryManager.exception.DuplicateResourceException;
import com.example.LibraryManager.exception.UnauthorizedException;
import com.example.LibraryManager.repositories.ClientRepository;
import com.example.LibraryManager.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService unit tests")
class UserServiceTest {
    @Mock UserRepository userRepository;
    @Mock ClientRepository clientRepository;
    @Mock PasswordEncoder passwordEncoder;
    @Mock StaffService staffService;
    @InjectMocks UserService userService;

    @Nested
    @DisplayName("login")
    class Login {
        @Test
        @DisplayName("should return the user when credentials are valid")
        void shouldReturnUserForValidCredentials() {
            User user = new User();
            user.setEmail("reader@example.com");
            user.setPassword("encoded");
            when(userRepository.findByEmail("reader@example.com")).thenReturn(user);
            when(passwordEncoder.matches("secret", "encoded")).thenReturn(true);

            User result = userService.login("reader@example.com", "secret");

            assertSame(user, result);
        }

        @Test
        @DisplayName("should reject invalid credentials without revealing which value is wrong")
        void shouldRejectInvalidCredentials() {
            when(userRepository.findByEmail("missing@example.com")).thenReturn(null);

            UnauthorizedException exception = assertThrows(UnauthorizedException.class,
                    () -> userService.login("missing@example.com", "secret"));

            assertEquals("Invalid email or password", exception.getMessage());
            verifyNoInteractions(passwordEncoder);
        }
    }

    @Nested
    @DisplayName("register")
    class Register {
        @Test
        @DisplayName("should reject an email that is already registered")
        void shouldRejectDuplicateEmail() {
            UserCreateRequest request = new UserCreateRequest();
            request.setEmail("reader@example.com");
            request.setPhone("0900000000");
            when(clientRepository.existsByPhone("0900000000")).thenReturn(false);
            when(userRepository.existsByEmail("reader@example.com")).thenReturn(true);

            assertThrows(DuplicateResourceException.class,
                    () -> userService.register(request));
            verify(userRepository, never()).save(any());
        }
    }
}
