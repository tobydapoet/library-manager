package com.example.LibraryManager.requests.user;

import com.example.LibraryManager.requests.staff.StaffCreateRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserStaffCreateRequest extends StaffCreateRequest {
    @NotBlank
    String email;

    @NotBlank
    String password;
}
