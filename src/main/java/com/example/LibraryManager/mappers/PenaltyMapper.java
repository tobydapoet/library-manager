package com.example.LibraryManager.mappers;

import com.example.LibraryManager.dtos.responses.PenaltyResponse;
import com.example.LibraryManager.entities.Penalty;
import org.springframework.stereotype.Component;

@Component
public class PenaltyMapper implements EntityMapper<Penalty, PenaltyResponse> {
    @Override
    public PenaltyResponse toResponse(Penalty entity) {
        if (entity == null) return null;
        return PenaltyResponse.builder().id(entity.getId())
                .borrowingId(entity.getBorrowing() == null ? null : entity.getBorrowing().getId())
                .quantity(entity.getQuality()).price(entity.getPrice())
                .billId(entity.getBill() == null ? null : entity.getBill().getId())
                .createdAt(entity.getCreatedAt()).build();
    }
}
