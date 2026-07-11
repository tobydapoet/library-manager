package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.Supplier;
import com.example.LibraryManager.requests.supplier.SupplierCreateRequest;
import com.example.LibraryManager.requests.supplier.SupplierUpdateRequest;
import com.example.LibraryManager.services.SupplierService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
@PreAuthorize("hasRole('STAFF')")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping()
    public Iterable<Supplier> findAll() {
        return supplierService.findAll();
    }

    @GetMapping("/search")
    public List<Supplier> findByName(@RequestParam("name") String name) {
        return supplierService.findByName(name);
    }

    @GetMapping("{id}")
    public Supplier findById(@PathVariable String id) {
        return supplierService.findById(id);
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('POSITION_LIBRARIAN','POSITION_ADMIN')")
    public Supplier create(@RequestBody @Valid() SupplierCreateRequest req) {
        return supplierService.create(req);
    }

    @PreAuthorize("hasAnyAuthority('POSITION_LIBRARIAN','POSITION_ADMIN')")
    @PatchMapping("{id}")
    public Supplier update(@PathVariable String id, @RequestBody @Valid() SupplierUpdateRequest req) {
        return supplierService.update(id, req);
    }

    @PreAuthorize("hasAnyAuthority('POSITION_LIBRARIAN','POSITION_ADMIN')")
    @PutMapping("{id}")
    public Supplier delete(@PathVariable String id) {
        return supplierService.softDelete(id);
    }
}
