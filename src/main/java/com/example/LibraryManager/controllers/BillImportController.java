package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.dtos.requests.BillImportRequest;
import com.example.LibraryManager.dtos.responses.BillImportResponse;
import com.example.LibraryManager.mappers.BillImportMapper;
import com.example.LibraryManager.services.BillImportService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bill_import")
@RequiredArgsConstructor
public class BillImportController {
    private final BillImportService billImportService;
    private final BillImportMapper billImportMapper;

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping()
    public Page<BillImportResponse> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "20") int size) {
        return billImportService.findAll(page, size).map(billImportMapper::toResponse);
    }

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/search")
    public Page<BillImportResponse> searchBySupplierName(
            @RequestParam String clientName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return billImportService.searchBySupplierName(clientName, page, size).map(billImportMapper::toResponse);
    }

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/{id}")
    public BillImportResponse findOne(@PathVariable String id) {
        return billImportMapper.toResponse(billImportService.findById(id));
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping()
    public BillImportResponse create(@RequestBody BillImportRequest req) {
        return billImportMapper.toResponse(billImportService.create(req));
    }

    @PreAuthorize("hasRole('STAFF')")
    @PutMapping("/{id}")
    public BillImportResponse calculate(@PathVariable String id) {
        return billImportMapper.toResponse(billImportService.calculateBillImportTotal(id));
    }

    @PreAuthorize("hasRole('STAFF')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        billImportService.deleteById(id);
    }
}
