package com.example.LibraryManager.requests.bill_import;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BillImportRequest {
    @NotBlank
    private String supplierId;

}
