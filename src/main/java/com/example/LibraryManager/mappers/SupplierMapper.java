package com.example.LibraryManager.mappers;

import com.example.LibraryManager.dtos.responses.SupplierResponse;
import com.example.LibraryManager.entities.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper implements EntityMapper<Supplier, SupplierResponse> {
    @Override
    public SupplierResponse toResponse(Supplier entity) {
        if (entity == null) return null;
        return SupplierResponse.builder().id(entity.getId()).name(entity.getName())
                .contact(entity.getContact()).address(entity.getAddress()).deleted(entity.isDeleted()).build();
    }
}
