package com.example.LibraryManager.services;

import com.example.LibraryManager.entities.BillImport;
import com.example.LibraryManager.entities.Purchase;
import com.example.LibraryManager.exception.ResourceInUseException;
import com.example.LibraryManager.repositories.BillImportRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BillImportService unit tests")
class BillImportServiceTest {
    @Mock BillImportRepository billImportRepository;
    @Mock SupplierService supplierService;
    @InjectMocks BillImportService billImportService;

    @Nested @DisplayName("calculateBillImportTotal")
    class CalculateTotal {
        @Test @DisplayName("should sum purchase prices instead of quantities")
        void shouldSumPurchasePrices() {
            BillImport bill = new BillImport(); bill.setId("import-1");
            Purchase first = new Purchase(); first.setPrice(100); first.setQuantity(2);
            Purchase second = new Purchase(); second.setPrice(70); second.setQuantity(5);
            bill.setPurchases(List.of(first, second));
            when(billImportRepository.findById("import-1")).thenReturn(Optional.of(bill));
            when(billImportRepository.save(bill)).thenReturn(bill);

            BillImport result = billImportService.calculateBillImportTotal("import-1");

            assertEquals(170, result.getTotal());
        }
    }

    @Nested @DisplayName("ensureMutable")
    class EnsureMutable {
        @Test @DisplayName("should reject a paid import bill")
        void shouldRejectPaidBill() {
            BillImport bill = new BillImport(); bill.setPaid(true);
            when(billImportRepository.findById("import-1")).thenReturn(Optional.of(bill));

            assertThrows(ResourceInUseException.class,
                    () -> billImportService.ensureMutable("import-1"));
        }
    }
}
