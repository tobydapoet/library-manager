package com.example.LibraryManager.mappers;

import com.example.LibraryManager.dtos.responses.BookResponse;
import com.example.LibraryManager.entities.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper implements EntityMapper<Book, BookResponse> {
    private final LocationMapper locationMapper;

    @Override
    public BookResponse toResponse(Book entity) {
        if (entity == null) return null;
        return BookResponse.builder().id(entity.getId()).title(entity.getTitle())
                .description(entity.getDescription()).imageUrl(entity.getImage_url()).author(entity.getAuthor())
                .status(entity.getStatus()).borrowPrice(entity.getBorrow_price()).sellPrice(entity.getSell_price())
                .importPrice(entity.getImport_price()).quantity(entity.getQuantity())
                .location(locationMapper.toResponse(entity.getLocation())).createdAt(entity.getCreatedAt()).build();
    }
}
