package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, String> {
    Page<Review> findByBook_Id(String id, Pageable pageable);

    @Query("""
            SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
            FROM Review r
            WHERE r.client.id = :clientId AND r.book.id = :bookId
            """)
    boolean existsByClientAndBook(
            @Param("clientId") String clientId,
            @Param("bookId") String bookId);
}
