package com.example.LibraryManager.mappers;

import com.example.LibraryManager.dtos.responses.BookCategoryResponse;
import com.example.LibraryManager.entities.BookCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookCategoryMapper implements EntityMapper<BookCategory, BookCategoryResponse> {
    private final BookMapper bookMapper;
    private final CategoryMapper categoryMapper;

    @Override
    public BookCategoryResponse toResponse(BookCategory entity) {
        if (entity == null) return null;
        return BookCategoryResponse.builder().id(entity.getId()).book(bookMapper.toResponse(entity.getBook()))
                .category(categoryMapper.toResponse(entity.getCategory())).build();
    }
}
