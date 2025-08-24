package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PenaltyRepository extends JpaRepository<Penalty, String> {
    List<Penalty> findByBorrowing_Id(String borrowerId);

    List<Penalty> findByBill_Id(String billId);
}
