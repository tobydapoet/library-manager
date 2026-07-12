package com.example.LibraryManager.services;

import com.example.LibraryManager.dtos.requests.SupplierCreateRequest;
import com.example.LibraryManager.dtos.requests.SupplierUpdateRequest;
import com.example.LibraryManager.entities.Supplier;
import com.example.LibraryManager.exception.ResourceNotFoundException;
import com.example.LibraryManager.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    @Cacheable(value = "uploadCache", key = "'supplier:all'")
    public Iterable<Supplier> findAll() {
        return supplierRepository.findByIsDeletedFalse();
    }

    public Supplier findById(String id) {
        return supplierRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Supplier not found"));
    }

    public List<Supplier> findByName(String name) {
        return supplierRepository.findByNameContaining(name);
    }

    @CacheEvict(value = "uploadCache", key = "'supplier:all'")
    public Supplier create(SupplierCreateRequest req) {
        Supplier supplier = new Supplier();
        supplier.setName(req.getName());
        supplier.setAddress(req.getAddress());
        supplier.setContact(req.getContact());

        return supplierRepository.save(supplier);
    }

    @CacheEvict(value = "uploadCache", key = "'supplier:all'")
    public Supplier update(String id, SupplierUpdateRequest req) {
        Supplier supplier = findById(id);
        supplier.setName(req.getName());
        supplier.setAddress(req.getAddress());
        supplier.setContact(req.getContact());
        return supplierRepository.save(supplier);
    }

    public Supplier softDelete(String id) {
        Supplier supplier = findById(id);
        supplier.setDeleted(true);
        return supplierRepository.save(supplier);
    }
}
