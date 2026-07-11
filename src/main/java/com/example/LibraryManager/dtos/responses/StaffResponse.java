package com.example.LibraryManager.dtos.responses;

import com.example.LibraryManager.enums.StaffPosition;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StaffResponse {
    String id;
    String name;
    String phone;
    StaffPosition position;
    Integer salary;
    boolean active;
    String avatarUrl;
    UserResponse user;
}
