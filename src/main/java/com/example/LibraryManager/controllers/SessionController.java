package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.dtos.requests.LoginRequest;
import com.example.LibraryManager.dtos.responses.AccessTokenResponse;
import com.example.LibraryManager.dtos.responses.AuthResponse;
import com.example.LibraryManager.dtos.responses.MessageStatusResponse;
import com.example.LibraryManager.dtos.responses.SessionResponse;
import com.example.LibraryManager.mappers.SessionMapper;
import com.example.LibraryManager.services.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;
    private final SessionMapper sessionMapper;

    @GetMapping("/{id}")
    public SessionResponse findById(@PathVariable String id) {
        return sessionMapper.toResponse(sessionService.findById(id));
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest dto) {
        return sessionMapper.toAuthResponse(sessionService.login(dto.getEmail(), dto.getPassword()));
    }

    @PostMapping("/refresh/{id}")
    public AccessTokenResponse refresh(@RequestHeader("Authorization") String refresh_token, @PathVariable String id) {
        if (refresh_token == null || !refresh_token.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String refreshToken = refresh_token.substring(7);
        return AccessTokenResponse.builder().accessToken(sessionService.refresh(refreshToken, id)).build();
    }

    @DeleteMapping("/logout/{id}")
    public ResponseEntity<MessageStatusResponse> logout(@PathVariable String id) {
        sessionService.logout(id);
        return ResponseEntity.ok(MessageStatusResponse.builder().message("Logout successful").build());
    }
}
