package com.example.LibraryManager.services;

import com.example.LibraryManager.entities.Book;
import com.example.LibraryManager.entities.Borrowing;
import com.example.LibraryManager.entities.Client;
import com.example.LibraryManager.repositories.BorrowingRepository;
import com.example.LibraryManager.requests.book.BookUpdateRequest;
import com.example.LibraryManager.requests.borrowing.BorrowingCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowingService {
    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private BillService billService;

    public Borrowing findById(String id) {
        return borrowingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find this borrowing"));
    }

    public List<Borrowing> findByBillId(String id) {
        return borrowingRepository.findByBill_Id(id);
    }

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
        borrowing.setBill(billService.findById(req.getBill_id()));
        borrowing.setPrice(quantity * book.getBorrow_price());
        borrowing.setExpiryDate(req.getExpiryDate());
        Borrowing savedBorrowing = borrowingRepository.save(borrowing);
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest();
        bookUpdateRequest.setQuantity(book.getQuantity() - savedBorrowing.getQuantity());
        bookService.update(req.getBook_id(), bookUpdateRequest);
        return savedBorrowing;
    }

    public void deleteById(String id) {
        borrowingRepository.deleteById(id);
    }

}
