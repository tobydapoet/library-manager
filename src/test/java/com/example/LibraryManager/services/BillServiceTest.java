package com.example.LibraryManager.services;

import com.example.LibraryManager.entities.*;
import com.example.LibraryManager.exception.BadRequestException;
import com.example.LibraryManager.exception.ResourceInUseException;
import com.example.LibraryManager.repositories.BillRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BillService unit tests")
class BillServiceTest {
    @Mock BillRepository billRepository;
    @Mock ClientService clientService;
    @InjectMocks BillService billService;

    @Nested
    @DisplayName("calculateBillTotal")
    class CalculateBillTotal {
        @Test
        @DisplayName("should calculate the total from borrowings, acquisitions, and penalties")
        void shouldCalculateTotal() {
            Bill bill = bill("bill-1", false);
            Borrowing borrowing = new Borrowing(); borrowing.setPrice(20);
            Acquisition acquisition = new Acquisition(); acquisition.setPrice(30);
            Penalty penalty = new Penalty(); penalty.setPrice(10);
            bill.setBorrowings(List.of(borrowing));
            bill.setAcquisitions(List.of(acquisition));
            bill.setPenalties(List.of(penalty));
            when(billRepository.findById("bill-1")).thenReturn(Optional.of(bill));
            when(billRepository.save(bill)).thenReturn(bill);

            Bill result = billService.calculateBillTotal("bill-1");

            assertEquals(60, result.getTotal());
            verify(billRepository).save(bill);
        }
    }

    @Nested
    @DisplayName("payment state")
    class PaymentState {
        @Test
        @DisplayName("should reject modifications to a paid bill")
        void shouldRejectPaidBillModification() {
            Bill bill = bill("bill-1", true);
            when(billRepository.findById("bill-1")).thenReturn(Optional.of(bill));

            assertThrows(ResourceInUseException.class,
                    () -> billService.ensureMutable("bill-1"));
        }

        @Test
        @DisplayName("should mark a bill as unpaid")
        void shouldMarkBillAsUnpaid() {
            Bill bill = bill("bill-1", true);
            when(billRepository.findById("bill-1")).thenReturn(Optional.of(bill));
            when(billRepository.save(bill)).thenReturn(bill);

            Bill result = billService.markAsUnpaid("bill-1");

            assertFalse(result.isPaid());
        }
    }

    @Nested
    @DisplayName("ensureMutableForClient")
    class EnsureMutableForClient {
        @Test
        @DisplayName("should reject a bill owned by another client")
        void shouldRejectAnotherClientsBill() {
            Bill bill = bill("bill-1", false);
            Client client = new Client(); client.setId("client-1");
            bill.setClient(client);
            when(billRepository.findById("bill-1")).thenReturn(Optional.of(bill));

            assertThrows(BadRequestException.class,
                    () -> billService.ensureMutableForClient("bill-1", "client-2"));
        }
    }

    private Bill bill(String id, boolean paid) {
        Bill bill = new Bill();
        bill.setId(id);
        bill.setPaid(paid);
        bill.setBorrowings(List.of());
        bill.setAcquisitions(List.of());
        bill.setPenalties(List.of());
        return bill;
    }
}
