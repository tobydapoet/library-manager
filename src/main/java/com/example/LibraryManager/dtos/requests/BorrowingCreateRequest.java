package com.example.LibraryManager.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class BorrowingCreateRequest {
    @NotBlank
    private String client_id;

    @NotBlank
    private String book_id;

    @NotNull
    private Integer price;

    @NotNull
    private Integer quantity;

    @NotNull
    private Date expiryDate;

    @NotBlank
    private String bill_id;

}
