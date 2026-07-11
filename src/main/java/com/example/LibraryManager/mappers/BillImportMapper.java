package com.example.LibraryManager.mappers;

import com.example.LibraryManager.dtos.responses.BillImportResponse;
import com.example.LibraryManager.entities.BillImport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BillImportMapper implements EntityMapper<BillImport, BillImportResponse> {
    private final SupplierMapper supplierMapper;

    @Override
    public BillImportResponse toResponse(BillImport entity) {
        if (entity == null) return null;
        return BillImportResponse.builder().id(entity.getId()).supplier(supplierMapper.toResponse(entity.getSupplier()))
                .total(entity.getTotal()).paid(entity.isPaid()).createdAt(entity.getCreatedAt()).build();
    }
}
