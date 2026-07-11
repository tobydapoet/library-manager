package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.dtos.requests.BorrowingCreateRequest;
import com.example.LibraryManager.dtos.responses.BorrowingResponse;
import com.example.LibraryManager.mappers.BorrowingMapper;
import com.example.LibraryManager.services.BorrowingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowing")
@RequiredArgsConstructor
public class BorrowingController {
    private final BorrowingService borrowingService;
    private final BorrowingMapper borrowingMapper;

    @GetMapping("/bill/{id}")
    public List<BorrowingResponse> findByBillId(@PathVariable String id) {
        return borrowingService.findByBillId(id).stream().map(borrowingMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public BorrowingResponse findById(@PathVariable String id) {
        return borrowingMapper.toResponse(borrowingService.findById(id));
    }

    @PostMapping()
    public BorrowingResponse create(@RequestBody BorrowingCreateRequest req) {
        return borrowingMapper.toResponse(borrowingService.create(req));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        borrowingService.deleteById(id);
    }
}
