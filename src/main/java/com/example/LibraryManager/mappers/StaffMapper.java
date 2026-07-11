package com.example.LibraryManager.mappers;

import com.example.LibraryManager.dtos.responses.StaffResponse;
import com.example.LibraryManager.entities.Staff;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StaffMapper implements EntityMapper<Staff, StaffResponse> {
    private final UserMapper userMapper;

    @Override
    public StaffResponse toResponse(Staff entity) {
        if (entity == null) return null;
        return StaffResponse.builder().id(entity.getId()).name(entity.getName()).phone(entity.getPhone())
                .position(entity.getPosition()).salary(entity.getSalary()).active(entity.isActive())
                .avatarUrl(entity.getAvatar_url()).user(userMapper.toResponse(entity.getUser())).build();
    }
}
