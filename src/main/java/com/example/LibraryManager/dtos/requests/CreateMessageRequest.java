package com.example.LibraryManager.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateMessageRequest {
    @NotBlank
    private String sender_id;

    @NotBlank
    private String receiver_id;

    @NotBlank
    private String content;

}
