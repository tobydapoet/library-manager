package com.example.LibraryManager.mappers;

import com.example.LibraryManager.dtos.responses.BorrowingResponse;
import com.example.LibraryManager.entities.Borrowing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BorrowingMapper implements EntityMapper<Borrowing, BorrowingResponse> {
    private final ClientMapper clientMapper;
    private final BookMapper bookMapper;

    @Override
    public BorrowingResponse toResponse(Borrowing entity) {
        if (entity == null) return null;
        return BorrowingResponse.builder().id(entity.getId()).client(clientMapper.toResponse(entity.getClient()))
                .book(bookMapper.toResponse(entity.getBook())).price(entity.getPrice()).quantity(entity.getQuantity())
                .expiryDate(entity.getExpiryDate()).createdAt(entity.getCreatedAt())
                .billId(entity.getBill() == null ? null : entity.getBill().getId()).build();
    }
}
