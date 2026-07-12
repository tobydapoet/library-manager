package com.example.LibraryManager.services;

import com.example.LibraryManager.entities.*;
import com.example.LibraryManager.enums.*;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JwtService unit tests")
class JwtServiceTest {
    private static final String SECRET = "this-is-a-test-secret-key-with-at-least-32-bytes";
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService(SECRET);
    }

    @Nested @DisplayName("access token")
    class AccessToken {
        @Test @DisplayName("should generate and parse client claims")
        void shouldGenerateClientClaims() {
            User user = new User(); user.setId("user-1"); user.setRole(Roles.client);
            Session session = new Session(); session.setId("session-1"); session.setUser(user);

            Claims claims = jwtService.parseAccessToken(jwtService.generateAccessToken(session));

            assertEquals("session-1", claims.getSubject());
            assertEquals("user-1", claims.get("id"));
            assertEquals("ROLE_CLIENT", claims.get("role"));
        }

        @Test @DisplayName("should include the staff position claim")
        void shouldIncludeStaffPosition() {
            Staff staff = new Staff(); staff.setPosition(StaffPosition.librarian);
            User user = new User(); user.setId("user-1"); user.setRole(Roles.staff); user.setStaff(staff);
            Session session = new Session(); session.setId("session-1"); session.setUser(user);

            Claims claims = jwtService.parseAccessToken(jwtService.generateAccessToken(session));

            assertEquals("POSITION_LIBRARIAN", claims.get("position"));
        }
    }

    @Nested @DisplayName("getAuthorities")
    class GetAuthorities {
        @Test @DisplayName("should create authorities for non-empty role and position")
        void shouldCreateAuthorities() {
            Collection<GrantedAuthority> authorities =
                    jwtService.getAuthorities("ROLE_STAFF", "POSITION_ADMIN");

            assertEquals(2, authorities.size());
            assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_STAFF")));
            assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("POSITION_ADMIN")));
        }
    }
}
