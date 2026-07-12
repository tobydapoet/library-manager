package com.example.LibraryManager.services;

import com.example.LibraryManager.dtos.requests.BorrowingCreateRequest;
import com.example.LibraryManager.entities.*;
import com.example.LibraryManager.repositories.BorrowingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BorrowingService unit tests")
class BorrowingServiceTest {
    @Mock BorrowingRepository borrowingRepository;
    @Mock BookService bookService;
    @Mock ClientService clientService;
    @Mock BillService billService;
    @InjectMocks BorrowingService borrowingService;

    @Nested
    @DisplayName("create")
    class Create {
        @Test
        @DisplayName("should create a borrowing, decrease stock, and recalculate the bill")
        void shouldCreateBorrowing() {
            BorrowingCreateRequest request = request(2);
            Book book = new Book();
            book.setId("book-1");
            book.setBorrow_price(15);
            Client client = new Client(); client.setId("client-1");
            Bill bill = new Bill(); bill.setId("bill-1");
            when(bookService.findById("book-1")).thenReturn(book);
            when(clientService.getClient("client-1")).thenReturn(client);
            when(billService.ensureMutableForClient("bill-1", "client-1")).thenReturn(bill);
            when(borrowingRepository.save(any(Borrowing.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            Borrowing result = borrowingService.create(request);

            assertEquals(2, result.getQuantity());
            assertEquals(30, result.getPrice());
            verify(bookService).decreaseStock("book-1", 2);
            verify(billService).calculateBillTotal("bill-1");
        }

        @Test
        @DisplayName("should use one as the default quantity")
        void shouldUseDefaultQuantity() {
            BorrowingCreateRequest request = request(null);
            Book book = new Book(); book.setId("book-1"); book.setBorrow_price(15);
            when(bookService.findById("book-1")).thenReturn(book);
            when(clientService.getClient("client-1")).thenReturn(new Client());
            when(billService.ensureMutableForClient("bill-1", "client-1")).thenReturn(new Bill());
            when(borrowingRepository.save(any(Borrowing.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            Borrowing result = borrowingService.create(request);

            assertEquals(1, result.getQuantity());
            assertEquals(15, result.getPrice());
        }
    }

    private BorrowingCreateRequest request(Integer quantity) {
        BorrowingCreateRequest request = new BorrowingCreateRequest();
        request.setBook_id("book-1");
        request.setClient_id("client-1");
        request.setBill_id("bill-1");
        request.setQuantity(quantity);
        request.setExpiryDate(new Date());
        return request;
    }
}
