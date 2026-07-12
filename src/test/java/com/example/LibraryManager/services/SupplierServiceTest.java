package com.example.LibraryManager.services;

import com.example.LibraryManager.entities.Supplier;
import com.example.LibraryManager.repositories.SupplierRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SupplierService unit tests")
class SupplierServiceTest {
    @Mock SupplierRepository supplierRepository;
    @InjectMocks SupplierService supplierService;

    @Nested @DisplayName("softDelete")
    class SoftDelete {
        @Test @DisplayName("should mark the supplier as deleted")
        void shouldMarkSupplierAsDeleted() {
            Supplier supplier = new Supplier(); supplier.setDeleted(false);
            when(supplierRepository.findById("supplier-1")).thenReturn(Optional.of(supplier));
            when(supplierRepository.save(supplier)).thenReturn(supplier);

            Supplier result = supplierService.softDelete("supplier-1");

            assertTrue(result.isDeleted());
        }
    }
}
