package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.BillImport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillImportRepository extends JpaRepository<BillImport, String> {
    Page<BillImport> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<BillImport> findBySupplier_NameContainingIgnoreCase(String supplier_name, Pageable pageable);
}


