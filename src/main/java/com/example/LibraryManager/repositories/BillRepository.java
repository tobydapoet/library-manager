package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, String> {
    Page<Bill> findByClient_Id(String client_id, Pageable pageable);

    Page<Bill> findByClient_NameContainingIgnoreCase(String client_name, Pageable pageable);
}
