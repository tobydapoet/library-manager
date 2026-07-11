package com.example.LibraryManager.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AcquisitionCreateRequest {
    @NotBlank
    private String client_id;

    @NotBlank
    private String book_id;

    private Integer quantity;

    @NotNull
    private int price;

    @NotBlank
    private String bill_id;

}
