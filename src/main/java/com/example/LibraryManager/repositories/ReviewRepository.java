package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {
    Page<Review> findByBook_Id(String id, Pageable pageable);
}
