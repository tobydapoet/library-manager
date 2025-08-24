package com.example.LibraryManager.requests.user;

import com.example.LibraryManager.requests.staff.StaffCreateRequest;
import jakarta.validation.constraints.NotBlank;

public class UserStaffCreateRequest extends StaffCreateRequest {
    @NotBlank
    String email;

    @NotBlank
    String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
