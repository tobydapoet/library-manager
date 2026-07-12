package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BillRepository extends JpaRepository<Bill, String> {
    Page<Bill> findByClient_Id(String client_id, Pageable pageable);

    @Query("""
            SELECT b FROM Bill b
            WHERE LOWER(b.client.name) LIKE LOWER(CONCAT('%', :clientName, '%'))
            """)
    Page<Bill> searchByClientName(@Param("clientName") String clientName, Pageable pageable);
}
