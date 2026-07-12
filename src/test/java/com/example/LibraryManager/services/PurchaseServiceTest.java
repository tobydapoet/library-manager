package com.example.LibraryManager.services;

import com.example.LibraryManager.dtos.requests.PurchaseCreateRequest;
import com.example.LibraryManager.entities.*;
import com.example.LibraryManager.repositories.PurchaseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PurchaseService unit tests")
class PurchaseServiceTest {
    @Mock PurchaseRepository purchaseRepository;
    @Mock BookService bookService;
    @Mock SupplierService supplierService;
    @Mock BillImportService billImportService;
    @InjectMocks PurchaseService purchaseService;

    @Nested
    @DisplayName("create")
    class Create {
        @Test
        @DisplayName("should create a purchase, increase stock, and recalculate the import bill")
        void shouldCreatePurchase() {
            PurchaseCreateRequest request = new PurchaseCreateRequest();
            request.setBook_id("book-1");
            request.setSupplier_id("supplier-1");
            request.setBillImport_id("import-1");
            request.setQuantity(3);
            Book book = new Book(); book.setId("book-1"); book.setImport_price(20);
            Supplier supplier = new Supplier(); supplier.setId("supplier-1");
            BillImport billImport = new BillImport(); billImport.setId("import-1");
            when(supplierService.findById("supplier-1")).thenReturn(supplier);
            when(bookService.findById("book-1")).thenReturn(book);
            when(billImportService.ensureMutableForSupplier("import-1", "supplier-1"))
                    .thenReturn(billImport);
            when(purchaseRepository.save(any(Purchase.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            Purchase result = purchaseService.create(request);

            assertEquals(3, result.getQuantity());
            assertEquals(60, result.getPrice());
            verify(bookService).increaseStock("book-1", 3);
            verify(billImportService).calculateBillImportTotal("import-1");
        }
    }
}
