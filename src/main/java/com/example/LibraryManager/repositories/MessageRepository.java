package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {
    @Query("""
            SELECT m FROM Message m
            WHERE m.sender.id = :userId OR m.receiver.id = :userId
            ORDER BY m.createdAt DESC
            """)
    List<Message> findConversationHistory(@Param("userId") String userId);

}
