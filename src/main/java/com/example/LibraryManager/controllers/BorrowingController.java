package com.example.LibraryManager.controllers;

import com.example.LibraryManager.entities.Borrowing;
import com.example.LibraryManager.requests.borrowing.BorrowingCreateRequest;
import com.example.LibraryManager.services.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowing")
public class BorrowingController {
    @Autowired
    private BorrowingService borrowingService;

    @GetMapping("/bill/{id}")
    public List<Borrowing> findByBillId(@PathVariable String id) {
        return borrowingService.findByBillId(id);
    }

    @GetMapping("/{id}")
    public Borrowing findById(@PathVariable String id) {
        return borrowingService.findById(id);
    }

    @PostMapping()
    public Borrowing create(@RequestBody BorrowingCreateRequest req) {
        return borrowingService.create(req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        borrowingService.deleteById(id);
    }
}
