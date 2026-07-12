package com.example.LibraryManager.services;

import com.example.LibraryManager.entities.*;
import com.example.LibraryManager.exception.UnauthorizedException;
import com.example.LibraryManager.repositories.SessionRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SessionService unit tests")
class SessionServiceTest {
    @Mock SessionRepository sessionRepository;
    @Mock UserService userService;
    @Mock JwtService jwtService;
    @InjectMocks SessionService sessionService;

    @Nested @DisplayName("login")
    class Login {
        @Test @DisplayName("should create a session and return both tokens")
        void shouldCreateSessionAndReturnTokens() {
            User user = new User();
            when(userService.login("reader@example.com", "secret")).thenReturn(user);
            when(jwtService.generateAccessToken(any())).thenReturn("access-token");
            when(jwtService.generateRefreshToken(any())).thenReturn("refresh-token");

            Map<String, String> result = sessionService.login("reader@example.com", "secret");

            assertEquals("access-token", result.get("access_token"));
            assertEquals("refresh-token", result.get("refresh_token"));
            verify(sessionRepository).save(argThat(session ->
                    session.getUser() == user && "refresh-token".equals(session.getToken())));
        }
    }

    @Nested @DisplayName("refresh")
    class Refresh {
        @Test @DisplayName("should reject a refresh token that does not match the session")
        void shouldRejectInvalidRefreshToken() {
            Session session = new Session(); session.setToken("expected");
            when(sessionRepository.findById("session-1")).thenReturn(Optional.of(session));

            assertThrows(UnauthorizedException.class,
                    () -> sessionService.refresh("invalid", "session-1"));
            verify(jwtService, never()).generateAccessToken(any());
        }
    }
}
