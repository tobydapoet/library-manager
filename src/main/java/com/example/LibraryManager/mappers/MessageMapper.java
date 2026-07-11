package com.example.LibraryManager.mappers;

import com.example.LibraryManager.dtos.responses.MessageResponse;
import com.example.LibraryManager.entities.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageMapper implements EntityMapper<Message, MessageResponse> {
    private final UserMapper userMapper;

    @Override
    public MessageResponse toResponse(Message entity) {
        if (entity == null) return null;
        return MessageResponse.builder().id(entity.getId()).sender(userMapper.toResponse(entity.getSender()))
                .receiver(userMapper.toResponse(entity.getReceiver())).content(entity.getContent())
                .createdAt(entity.getCreatedAt()).deleted(entity.isDeleted()).read(entity.isRead()).build();
    }
}
