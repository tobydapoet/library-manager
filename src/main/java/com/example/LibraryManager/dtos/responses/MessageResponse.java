package com.example.LibraryManager.dtos.responses;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class MessageResponse {
    String id;
    UserResponse sender;
    UserResponse receiver;
    String content;
    Date createdAt;
    boolean deleted;
    boolean read;
}
