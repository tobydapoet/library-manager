package com.example.LibraryManager.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookCategoryRequest {
    @NotBlank
    private String book_id;

    @NotNull
    private Integer category_id;

}
