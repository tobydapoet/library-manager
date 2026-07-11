package com.example.LibraryManager.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BillImportRequest {
    @NotBlank
    private String supplierId;

}
