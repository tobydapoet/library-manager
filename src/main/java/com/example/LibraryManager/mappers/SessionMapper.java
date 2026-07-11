package com.example.LibraryManager.mappers;

import com.example.LibraryManager.dtos.responses.AuthResponse;
import com.example.LibraryManager.dtos.responses.SessionResponse;
import com.example.LibraryManager.entities.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SessionMapper implements EntityMapper<Session, SessionResponse> {
    private final UserMapper userMapper;

    @Override
    public SessionResponse toResponse(Session entity) {
        if (entity == null) return null;
        return SessionResponse.builder().id(entity.getId()).user(userMapper.toResponse(entity.getUser()))
                .createdAt(entity.getCreatedAt()).build();
    }

    public AuthResponse toAuthResponse(Map<String, String> tokens) {
        return AuthResponse.builder().accessToken(tokens.get("access_token"))
                .refreshToken(tokens.get("refresh_token")).build();
    }
}
