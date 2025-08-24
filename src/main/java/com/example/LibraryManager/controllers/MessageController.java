package com.example.LibraryManager.controllers;

import com.example.LibraryManager.entities.Message;
import com.example.LibraryManager.requests.message.CreateMessageRequest;
import com.example.LibraryManager.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @MessageMapping("/send")
    public void sendMessage(CreateMessageRequest req) {
        Message saved = messageService.create(req);
        simpMessagingTemplate.convertAndSendToUser(saved.getReceiver().getId(),
                "/queue/message", saved);

        simpMessagingTemplate.convertAndSendToUser(saved.getSender().getId(),
                "/queue/message", saved
        );
    }

    @GetMapping("/{id}")
    public Message findById(@PathVariable String id) {
        return messageService.findById(id);
    }

    @GetMapping("/user/{id}")
    public List<Message> findByUserId(@PathVariable String id) {
        return messageService.findByUserid(id);
    }

    @PutMapping("/{id}")
    public Message update(@PathVariable String id, @RequestBody String content) {
        return messageService.update(id, content);
    }

    @PutMapping("/delete/{id}")
    public Message softDelete(@PathVariable String id) {
        return messageService.softDelete(id);
    }

    @PutMapping("/read/{id}")
    public Message isRead(@PathVariable String id) {
        return messageService.isRead(id);
    }

}
