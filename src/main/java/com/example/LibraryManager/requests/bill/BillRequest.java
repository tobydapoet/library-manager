package com.example.LibraryManager.requests.bill;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BillRequest {
    @NotBlank
    private String clientId;

}
