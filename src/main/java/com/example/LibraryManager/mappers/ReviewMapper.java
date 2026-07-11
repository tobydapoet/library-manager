package com.example.LibraryManager.mappers;

import com.example.LibraryManager.dtos.responses.ReviewResponse;
import com.example.LibraryManager.entities.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMapper implements EntityMapper<Review, ReviewResponse> {
    private final ClientMapper clientMapper;
    private final BookMapper bookMapper;

    @Override
    public ReviewResponse toResponse(Review entity) {
        if (entity == null) return null;
        return ReviewResponse.builder().id(entity.getId()).client(clientMapper.toResponse(entity.getClient()))
                .book(bookMapper.toResponse(entity.getBook())).rating(entity.getRating()).comment(entity.getComment())
                .createdAt(entity.getCreatedAt()).build();
    }
}
