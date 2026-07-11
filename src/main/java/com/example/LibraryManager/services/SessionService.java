package com.example.LibraryManager.services;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.Session;
import com.example.LibraryManager.entities.User;
import com.example.LibraryManager.exception.ResourceNotFoundException;
import com.example.LibraryManager.exception.UnauthorizedException;
import com.example.LibraryManager.repositories.SessionRepository;
import com.example.LibraryManager.dtos.requests.UserGoogleCreateRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;

    private final UserService userService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public Session findById(String id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found with id: " + id));
    }

    public Map<String, String> login(String email, String password) {
        User existingUser = userService.login(email, password);
        Session session = new Session();
        session.setUser(existingUser);
        String access_token = jwtService.generateAccessToken(session);
        String refresh_token = jwtService.generateRefreshToken(session);
        session.setToken(refresh_token);

        sessionRepository.save(session);

        return Map.of(
                "access_token", access_token,
                "refresh_token", refresh_token
        );
    }

    public String refresh(String refresh_token, String id) {
        Session session = findById(id);
        if (!session.getToken().equals(refresh_token)) {
            throw new UnauthorizedException("Invalid refresh token");
        }
        return jwtService.generateAccessToken(session);
    }

    public void logout(String id) {
        sessionRepository.deleteById(id);
    }

    public Map<String, String> googleLogin(UserGoogleCreateRequest dto) {
        User existingUser = userService.findByEmail(dto.getEmail());
        if (existingUser == null) {
            existingUser = userService.createWithGoogle(dto);
        }
        System.out.println("existingUser: " + existingUser);

        Session session = new Session();
        session.setUser(existingUser);

        String access_token = jwtService.generateAccessToken(session);
        String refresh_token = jwtService.generateRefreshToken(session);
        session.setToken(refresh_token);

        sessionRepository.save(session);

        return Map.of(
                "access_token", access_token,
                "refresh_token", refresh_token
        );
    }

}
