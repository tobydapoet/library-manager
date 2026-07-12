package com.example.LibraryManager.services;

import com.example.LibraryManager.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.Message;
import com.example.LibraryManager.entities.User;
import com.example.LibraryManager.repositories.MessageRepository;
import com.example.LibraryManager.dtos.requests.CreateMessageRequest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    private final UserService userService;

    public Message findById(String id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
    }

    @Cacheable(value = "uploadCache", key = "'message:' + #userId")
    public List<Message> findByUserid(String userId) {
        return messageRepository.findConversationHistory(userId);
    }

    @Caching(evict = {
            @CacheEvict(value = "uploadCache", key = "'message:' + #req.sender_id"),
            @CacheEvict(value = "uploadCache", key = "'message:' + #req.receiver_id")
    })
    public Message create(CreateMessageRequest req) {
        Message message = new Message();
        User sender = userService.findById(req.getSender_id());
        User receiver = userService.findById(req.getReceiver_id());
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(req.getContent());
        return messageRepository.save(message);
    }

    @CacheEvict(value = "uploadCache", allEntries = true)
    public Message update(String id, String content) {
        Message message = findById(id);
        message.setContent(content);
        return messageRepository.save(message);
    }

    @CacheEvict(value = "uploadCache", allEntries = true)
    public Message softDelete(String id) {
        Message message = findById(id);
        message.setDeleted(true);
        return messageRepository.save(message);
    }

    @CacheEvict(value = "uploadCache", allEntries = true)
    public Message isRead(String id) {
        Message message = findById(id);
        message.setRead(true);
        return messageRepository.save(message);
    }
}
