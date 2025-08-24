package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {
    boolean existsByPhone(String phone);

    Page<Client> findByPhoneContainingIgnoreCaseOrNameContainingIgnoreCaseOrUserEmailContainingIgnoreCase(String phone, String name, String email, Pageable pageable);
}
