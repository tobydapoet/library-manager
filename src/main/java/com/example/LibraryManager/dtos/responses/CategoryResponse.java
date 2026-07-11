package com.example.LibraryManager.dtos.responses;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryResponse {
    int id;
    String name;
}
