package com.example.LibraryManager.services;

import com.example.LibraryManager.dtos.requests.AcquisitionCreateRequest;
import com.example.LibraryManager.entities.*;
import com.example.LibraryManager.repositories.AcquisitionRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AcquisitionService unit tests")
class AcquisitionServiceTest {
    @Mock AcquisitionRepository acquisitionRepository;
    @Mock BookService bookService;
    @Mock ClientService clientService;
    @Mock BillService billService;
    @InjectMocks AcquisitionService acquisitionService;

    @Nested @DisplayName("create")
    class Create {
        @Test @DisplayName("should create an acquisition and decrease stock")
        void shouldCreateAcquisition() {
            AcquisitionCreateRequest request = new AcquisitionCreateRequest();
            request.setBook_id("book-1"); request.setClient_id("client-1");
            request.setBill_id("bill-1"); request.setQuantity(2);
            Book book = new Book(); book.setId("book-1"); book.setSell_price(40);
            when(bookService.findById("book-1")).thenReturn(book);
            when(clientService.getClient("client-1")).thenReturn(new Client());
            when(billService.ensureMutableForClient("bill-1", "client-1")).thenReturn(new Bill());
            when(acquisitionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

            Acquisition result = acquisitionService.create(request);

            assertEquals(2, result.getQuantity());
            assertEquals(80, result.getPrice());
            verify(bookService).decreaseStock("book-1", 2);
            verify(billService).calculateBillTotal("bill-1");
        }
    }
}
