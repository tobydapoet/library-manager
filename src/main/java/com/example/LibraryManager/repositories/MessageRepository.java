package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {
    List<Message> findBySender_IdOrReceiver_IdOrderByCreatedAtDesc(String senderId, String receiverId);

}
