package com.example.LibraryManager.mappers;

import com.example.LibraryManager.dtos.responses.AcquisitionResponse;
import com.example.LibraryManager.entities.Acquisition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AcquisitionMapper implements EntityMapper<Acquisition, AcquisitionResponse> {
    private final ClientMapper clientMapper;
    private final BookMapper bookMapper;

    @Override
    public AcquisitionResponse toResponse(Acquisition entity) {
        if (entity == null) return null;
        return AcquisitionResponse.builder().id(entity.getId()).client(clientMapper.toResponse(entity.getClient()))
                .book(bookMapper.toResponse(entity.getBook())).quantity(entity.getQuantity()).price(entity.getPrice())
                .billId(entity.getBill() == null ? null : entity.getBill().getId())
                .createdAt(entity.getCreatedAt()).build();
    }
}
