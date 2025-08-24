package com.example.LibraryManager.repositories;


import com.example.LibraryManager.entities.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing, String> {
    List<Borrowing> findByBill_Id(String borrowingId);
}
