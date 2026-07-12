package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.Category;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT bc.category FROM BookCategory bc WHERE bc.book.id = :bookId")
    List<Category> findByBookId(@Param("bookId") String bookId);
}
