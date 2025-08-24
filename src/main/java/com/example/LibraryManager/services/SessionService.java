package com.example.LibraryManager.services;

import com.example.LibraryManager.entities.Session;
import com.example.LibraryManager.entities.User;
import com.example.LibraryManager.repositories.SessionRepository;
import com.example.LibraryManager.requests.user.UserGoogleCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SessionService {
    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Session findById(String id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + id));
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
            throw new RuntimeException("Invalid refresh token");
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
