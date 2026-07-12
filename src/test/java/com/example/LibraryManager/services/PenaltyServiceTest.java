package com.example.LibraryManager.services;

import com.example.LibraryManager.dtos.requests.PenaltyCreateRequest;
import com.example.LibraryManager.entities.*;
import com.example.LibraryManager.exception.BadRequestException;
import com.example.LibraryManager.repositories.PenaltyRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PenaltyService unit tests")
class PenaltyServiceTest {
    @Mock PenaltyRepository penaltyRepository;
    @Mock BorrowingService borrowingService;
    @Mock BillService billService;
    @InjectMocks PenaltyService penaltyService;

    @Nested @DisplayName("create")
    class Create {
        @Test @DisplayName("should calculate penalty price from book sell price")
        void shouldCalculatePenaltyPrice() {
            PenaltyCreateRequest request = request("bill-1");
            Book book = new Book(); book.setSell_price(25);
            Bill bill = new Bill(); bill.setId("bill-1");
            Borrowing borrowing = new Borrowing(); borrowing.setBook(book); borrowing.setBill(bill);
            when(borrowingService.findById("borrowing-1")).thenReturn(borrowing);
            when(billService.ensureMutable("bill-1")).thenReturn(bill);
            when(penaltyRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

            Penalty result = penaltyService.create(request);

            assertEquals(2, result.getQuality());
            assertEquals(50, result.getPrice());
            verify(billService).calculateBillTotal("bill-1");
        }

        @Test @DisplayName("should reject a borrowing from another bill")
        void shouldRejectMismatchedBill() {
            PenaltyCreateRequest request = request("bill-2");
            Bill bill = new Bill(); bill.setId("bill-1");
            Borrowing borrowing = new Borrowing(); borrowing.setBill(bill);
            when(borrowingService.findById("borrowing-1")).thenReturn(borrowing);

            assertThrows(BadRequestException.class, () -> penaltyService.create(request));
        }
    }

    private PenaltyCreateRequest request(String billId) {
        PenaltyCreateRequest request = new PenaltyCreateRequest();
        request.setBorrowing_id("borrowing-1"); request.setBill_id(billId); request.setQuantity(2);
        return request;
    }
}
