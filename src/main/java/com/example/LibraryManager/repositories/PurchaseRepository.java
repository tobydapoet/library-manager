package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, String> {
    List<Purchase> findByBillImport_Id(String billImport);
}
