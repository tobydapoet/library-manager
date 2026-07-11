package com.example.LibraryManager.dtos.responses;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BookCategoryResponse {
    Long id;
    BookResponse book;
    CategoryResponse category;
}
