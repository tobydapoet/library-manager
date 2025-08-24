package com.example.LibraryManager.repositories;


import com.example.LibraryManager.entities.Acquisition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcquisitionRepository extends JpaRepository<Acquisition, String> {
    List<Acquisition> findByBill_Id(String billId);
}
