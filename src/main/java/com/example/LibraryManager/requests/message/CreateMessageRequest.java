package com.example.LibraryManager.requests.message;

import jakarta.validation.constraints.NotBlank;

public class CreateMessageRequest {
    @NotBlank
    private String sender_id;

    @NotBlank
    private String receiver_id;

    @NotBlank
    private String content;

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
