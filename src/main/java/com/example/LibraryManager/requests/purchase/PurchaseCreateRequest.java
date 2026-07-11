package com.example.LibraryManager.requests.purchase;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PurchaseCreateRequest {
    @NotBlank
    private String book_id;

    @NotBlank
    private String supplier_id;


    @NotNull
    private Integer quantity;

    @NotBlank
    private String billImport_id;

}
