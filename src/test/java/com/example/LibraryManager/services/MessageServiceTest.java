package com.example.LibraryManager.services;

import com.example.LibraryManager.dtos.requests.CreateMessageRequest;
import com.example.LibraryManager.entities.*;
import com.example.LibraryManager.repositories.MessageRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MessageService unit tests")
class MessageServiceTest {
    @Mock MessageRepository messageRepository;
    @Mock UserService userService;
    @InjectMocks MessageService messageService;

    @Nested @DisplayName("create")
    class Create {
        @Test @DisplayName("should assign sender, receiver, and content")
        void shouldCreateMessage() {
            CreateMessageRequest request = new CreateMessageRequest();
            request.setSender_id("sender"); request.setReceiver_id("receiver"); request.setContent("Hello");
            User sender = new User(); User receiver = new User();
            when(userService.findById("sender")).thenReturn(sender);
            when(userService.findById("receiver")).thenReturn(receiver);
            when(messageRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

            Message result = messageService.create(request);

            assertSame(sender, result.getSender());
            assertSame(receiver, result.getReceiver());
            assertEquals("Hello", result.getContent());
        }
    }
}
