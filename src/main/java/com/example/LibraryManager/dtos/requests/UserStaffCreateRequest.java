package com.example.LibraryManager.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserStaffCreateRequest extends StaffCreateRequest {
    @NotBlank
    String email;

    @NotBlank
    String password;
}
