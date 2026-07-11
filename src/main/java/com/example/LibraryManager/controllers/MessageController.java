package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.Message;
import com.example.LibraryManager.dtos.requests.CreateMessageRequest;
import com.example.LibraryManager.dtos.responses.MessageResponse;
import com.example.LibraryManager.mappers.MessageMapper;
import com.example.LibraryManager.services.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final MessageMapper messageMapper;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/send")
    public void sendMessage(CreateMessageRequest req) {
        Message saved = messageService.create(req);
        MessageResponse response = messageMapper.toResponse(saved);
        simpMessagingTemplate.convertAndSendToUser(saved.getReceiver().getId(),
                "/queue/message", response);

        simpMessagingTemplate.convertAndSendToUser(saved.getSender().getId(),
                "/queue/message", response
        );
    }

    @GetMapping("/{id}")
    public MessageResponse findById(@PathVariable String id) {
        return messageMapper.toResponse(messageService.findById(id));
    }

    @GetMapping("/user/{id}")
    public List<MessageResponse> findByUserId(@PathVariable String id) {
        return messageService.findByUserid(id).stream().map(messageMapper::toResponse).toList();
    }

    @PutMapping("/{id}")
    public MessageResponse update(@PathVariable String id, @RequestBody String content) {
        return messageMapper.toResponse(messageService.update(id, content));
    }

    @PutMapping("/delete/{id}")
    public MessageResponse softDelete(@PathVariable String id) {
        return messageMapper.toResponse(messageService.softDelete(id));
    }

    @PutMapping("/read/{id}")
    public MessageResponse isRead(@PathVariable String id) {
        return messageMapper.toResponse(messageService.isRead(id));
    }

}
