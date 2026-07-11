package com.example.LibraryManager.requests.review;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewCreateRequest {
    @NotBlank
    private String clientId;

    @NotBlank
    private String bookId;

    @NotNull
    private int rating;

    @NotNull
    private String comment;

}
