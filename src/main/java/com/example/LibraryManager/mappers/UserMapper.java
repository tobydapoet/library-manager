package com.example.LibraryManager.mappers;

import com.example.LibraryManager.dtos.responses.UserResponse;
import com.example.LibraryManager.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements EntityMapper<User, UserResponse> {
    @Override
    public UserResponse toResponse(User entity) {
        if (entity == null) return null;
        return UserResponse.builder().id(entity.getId()).email(entity.getEmail()).role(entity.getRole())
                .providedId(entity.getProvided_id()).createdAt(entity.getCreatedAt()).build();
    }
}
