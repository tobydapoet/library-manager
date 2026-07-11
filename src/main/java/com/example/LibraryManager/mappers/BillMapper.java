package com.example.LibraryManager.mappers;

import com.example.LibraryManager.dtos.responses.BillResponse;
import com.example.LibraryManager.entities.Bill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BillMapper implements EntityMapper<Bill, BillResponse> {
    private final ClientMapper clientMapper;

    @Override
    public BillResponse toResponse(Bill entity) {
        if (entity == null) return null;
        return BillResponse.builder().id(entity.getId()).client(clientMapper.toResponse(entity.getClient()))
                .total(entity.getTotal()).paid(entity.isPaid()).createdAt(entity.getCreatedAt()).build();
    }
}
