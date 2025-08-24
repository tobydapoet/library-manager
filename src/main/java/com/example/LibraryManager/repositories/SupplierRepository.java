package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, String> {
    List<Supplier> findByNameContaining(String name);

    List<Supplier> findByIsDeletedFalse();
}
