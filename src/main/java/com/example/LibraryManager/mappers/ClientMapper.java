package com.example.LibraryManager.mappers;

import com.example.LibraryManager.dtos.responses.ClientResponse;
import com.example.LibraryManager.entities.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientMapper implements EntityMapper<Client, ClientResponse> {
    private final UserMapper userMapper;

    @Override
    public ClientResponse toResponse(Client entity) {
        if (entity == null) return null;
        return ClientResponse.builder().id(entity.getId()).name(entity.getName()).phone(entity.getPhone())
                .dob(entity.getDob()).address(entity.getAddress()).avatarUrl(entity.getAvatar_url())
                .user(userMapper.toResponse(entity.getUser())).build();
    }
}
