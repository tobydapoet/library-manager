package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.dtos.requests.PurchaseCreateRequest;
import com.example.LibraryManager.dtos.responses.PurchaseResponse;
import com.example.LibraryManager.mappers.PurchaseMapper;
import com.example.LibraryManager.services.PurchaseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final PurchaseMapper purchaseMapper;

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','ADMIN')")
    @GetMapping("/bill/{id}")
    public List<PurchaseResponse> findByBillId(@PathVariable String id) {
        return purchaseService.findByBillId(id).stream().map(purchaseMapper::toResponse).toList();
    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','ADMIN')")
    @GetMapping("/{id}")
    public PurchaseResponse findById(@PathVariable String id) {
        return purchaseMapper.toResponse(purchaseService.findById(id));
    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','ADMIN')")
    @PostMapping()
    public PurchaseResponse create(@RequestBody PurchaseCreateRequest req) {
        return purchaseMapper.toResponse(purchaseService.create(req));
    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        purchaseService.deleteById(id);
    }
}
