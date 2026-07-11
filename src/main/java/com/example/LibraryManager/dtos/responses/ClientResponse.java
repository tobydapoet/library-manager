package com.example.LibraryManager.dtos.responses;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class ClientResponse {
    String id;
    String name;
    String phone;
    Date dob;
    String address;
    String avatarUrl;
    UserResponse user;
}
