package com.example.LibraryManager.dtos.responses;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class ErrorResponse {
    String code;
    String message;
    Object details;
    LocalDateTime timestamp;
}
