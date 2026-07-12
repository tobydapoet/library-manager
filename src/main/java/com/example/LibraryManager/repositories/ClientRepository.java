package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends JpaRepository<Client, String> {
    boolean existsByPhone(String phone);

    @Query("""
            SELECT c FROM Client c
            LEFT JOIN c.user u
            WHERE LOWER(c.phone) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<Client> search(@Param("keyword") String keyword, Pageable pageable);
}
