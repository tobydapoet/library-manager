package com.example.LibraryManager.controllers;

import com.example.LibraryManager.entities.BillImport;
import com.example.LibraryManager.requests.bill_import.BillImportRequest;
import com.example.LibraryManager.services.BillImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bill_import")
public class BillImportController {
    @Autowired
    private BillImportService billImportService;

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping()
    public Page<BillImport> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "20") int size) {
        return billImportService.findAll(page, size);
    }

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/search")
    public Page<BillImport> searchBySupplierName(
            @RequestParam String clientName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return billImportService.searchBySupplierName(clientName, page, size);
    }

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/{id}")
    public BillImport findOne(@PathVariable String id) {
        return billImportService.findById(id);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping()
    public BillImport create(@RequestBody BillImportRequest req) {
        return billImportService.create(req);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PutMapping("/{id}")
    public BillImport calculate(@PathVariable String id) {
        return billImportService.calculateBillImportTotal(id);
    }

    @PreAuthorize("hasRole('STAFF')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        billImportService.deleteById(id);
    }
}
