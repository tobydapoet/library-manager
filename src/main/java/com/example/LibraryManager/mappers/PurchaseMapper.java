package com.example.LibraryManager.mappers;

import com.example.LibraryManager.dtos.responses.PurchaseResponse;
import com.example.LibraryManager.entities.Purchase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PurchaseMapper implements EntityMapper<Purchase, PurchaseResponse> {
    private final BookMapper bookMapper;
    private final SupplierMapper supplierMapper;

    @Override
    public PurchaseResponse toResponse(Purchase entity) {
        if (entity == null) return null;
        return PurchaseResponse.builder().id(entity.getId()).book(bookMapper.toResponse(entity.getBook()))
                .supplier(supplierMapper.toResponse(entity.getSupplier())).price(entity.getPrice())
                .quantity(entity.getQuantity())
                .billImportId(entity.getBillImport() == null ? null : entity.getBillImport().getId())
                .createdAt(entity.getCreatedAt()).build();
    }
}
