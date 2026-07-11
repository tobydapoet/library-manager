package com.example.LibraryManager.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BillRequest {
    @NotBlank
    private String clientId;

}
