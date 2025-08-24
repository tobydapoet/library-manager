package com.example.LibraryManager.requests.bill_import;

import jakarta.validation.constraints.NotBlank;

public class BillImportRequest {
    @NotBlank
    private String supplierId;

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
    }
}
