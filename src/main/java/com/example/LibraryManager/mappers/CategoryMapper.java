package com.example.LibraryManager.mappers;

import com.example.LibraryManager.dtos.responses.CategoryResponse;
import com.example.LibraryManager.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements EntityMapper<Category, CategoryResponse> {
    @Override
    public CategoryResponse toResponse(Category entity) {
        if (entity == null) return null;
        return CategoryResponse.builder().id(entity.getId()).name(entity.getName()).build();
    }
}
