package com.example.LibraryManager.services;

import com.example.LibraryManager.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.Book;
import com.example.LibraryManager.entities.Borrowing;
import com.example.LibraryManager.entities.Client;
import com.example.LibraryManager.repositories.BorrowingRepository;
import com.example.LibraryManager.dtos.requests.BorrowingCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowingService {
    private final BorrowingRepository borrowingRepository;

    private final BookService bookService;

    private final ClientService clientService;

    private final BillService billService;

    public Borrowing findById(String id) {
        return borrowingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find this borrowing"));
    }

    public List<Borrowing> findByBillId(String id) {
        return borrowingRepository.findByBill_Id(id);
    }

    @Transactional
    public Borrowing create(BorrowingCreateRequest req) {
        Borrowing borrowing = new Borrowing();
        Integer quantity = 1;
        Book book = bookService.findById(req.getBook_id());
        Client client = clientService.getClient(req.getClient_id());
        borrowing.setClient(client);
        borrowing.setBook(book);
        if (req.getQuantity() != null) {
            borrowing.setQuantity(req.getQuantity());
            quantity = req.getQuantity();
        }
        borrowing.setBill(billService.ensureMutableForClient(req.getBill_id(), req.getClient_id()));
        borrowing.setPrice(quantity * book.getBorrow_price());
        borrowing.setExpiryDate(req.getExpiryDate());
        bookService.decreaseStock(book.getId(), quantity);
        Borrowing savedBorrowing = borrowingRepository.save(borrowing);
        billService.calculateBillTotal(req.getBill_id());
        return savedBorrowing;
    }

    @Transactional
    public void deleteById(String id) {
        Borrowing borrowing = findById(id);
        String billId = borrowing.getBill().getId();
        billService.ensureMutable(billId);
        borrowingRepository.delete(borrowing);
        bookService.increaseStock(borrowing.getBook().getId(), borrowing.getQuantity());
        billService.calculateBillTotal(billId);
    }

}
