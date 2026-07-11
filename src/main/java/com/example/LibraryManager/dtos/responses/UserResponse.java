package com.example.LibraryManager.dtos.responses;

import com.example.LibraryManager.enums.Roles;
import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class UserResponse {
    String id;
    String email;
    Roles role;
    String providedId;
    Date createdAt;
}
