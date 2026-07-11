package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.dtos.requests.SupplierCreateRequest;
import com.example.LibraryManager.dtos.requests.SupplierUpdateRequest;
import com.example.LibraryManager.dtos.responses.SupplierResponse;
import com.example.LibraryManager.mappers.SupplierMapper;
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
    private final SupplierMapper supplierMapper;

    @GetMapping()
    public List<SupplierResponse> findAll() {
        return supplierMapper.toResponses(supplierService.findAll());
    }

    @GetMapping("/search")
    public List<SupplierResponse> findByName(@RequestParam("name") String name) {
        return supplierService.findByName(name).stream().map(supplierMapper::toResponse).toList();
    }

    @GetMapping("{id}")
    public SupplierResponse findById(@PathVariable String id) {
        return supplierMapper.toResponse(supplierService.findById(id));
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('POSITION_LIBRARIAN','POSITION_ADMIN')")
    public SupplierResponse create(@RequestBody @Valid() SupplierCreateRequest req) {
        return supplierMapper.toResponse(supplierService.create(req));
    }

    @PreAuthorize("hasAnyAuthority('POSITION_LIBRARIAN','POSITION_ADMIN')")
    @PatchMapping("{id}")
    public SupplierResponse update(@PathVariable String id, @RequestBody @Valid() SupplierUpdateRequest req) {
        return supplierMapper.toResponse(supplierService.update(id, req));
    }

    @PreAuthorize("hasAnyAuthority('POSITION_LIBRARIAN','POSITION_ADMIN')")
    @PutMapping("{id}")
    public SupplierResponse delete(@PathVariable String id) {
        return supplierMapper.toResponse(supplierService.softDelete(id));
    }
}
