package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.Session;
import com.example.LibraryManager.requests.session.LoginRequest;
import com.example.LibraryManager.services.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @GetMapping("/{id}")
    public Session findById(@PathVariable String id) {
        return sessionService.findById(id);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest dto) {
        return sessionService.login(dto.getEmail(), dto.getPassword());
    }

    @PostMapping("/refresh/{id}")
    public String refresh(@RequestHeader("Authorization") String refresh_token, @PathVariable String id) {
        if (refresh_token == null || !refresh_token.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String refreshToken = refresh_token.substring(7);
        return sessionService.refresh(refreshToken, id);
    }

    @DeleteMapping("/logout/{id}")
    public ResponseEntity<Map<String, String>> logout(@PathVariable String id) {
        sessionService.logout(id);
        return ResponseEntity.ok(Map.of("message", "Logout successful"));
    }
}
