package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.Purchase;
import com.example.LibraryManager.requests.purchase.PurchaseCreateRequest;
import com.example.LibraryManager.services.PurchaseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','ADMIN')")
    @GetMapping("/bill/{id}")
    public List<Purchase> findByBillId(@PathVariable String id) {
        return purchaseService.findByBillId(id);
    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','ADMIN')")
    @GetMapping("/{id}")
    public Purchase findById(@PathVariable String id) {
        return purchaseService.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','ADMIN')")
    @PostMapping()
    public Purchase create(@RequestBody PurchaseCreateRequest req) {
        return purchaseService.create(req);
    }

    @PreAuthorize("hasAnyAuthority('LIBRARIAN','ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        purchaseService.deleteById(id);
    }
}
