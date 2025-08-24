package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, String> {
    boolean existsByPhone(String phone);

    Iterable<Staff> findByPhoneContainingIgnoreCaseOrNameContainingIgnoreCaseOrUserEmailContainingIgnoreCase(String phone, String name, String email);

}
