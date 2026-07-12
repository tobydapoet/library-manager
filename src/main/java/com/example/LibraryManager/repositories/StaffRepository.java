package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StaffRepository extends JpaRepository<Staff, String> {
    @Query("""
            SELECT s FROM Staff s
            LEFT JOIN s.user u
            WHERE LOWER(s.phone) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Iterable<Staff> search(@Param("keyword") String keyword);

}
