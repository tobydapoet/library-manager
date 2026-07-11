package com.example.LibraryManager.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PenaltyCreateRequest {
    @NotBlank
    private String borrowing_id;

    @NotNull
    private Integer quantity;

    @NotBlank
    private String bill_id;

}
