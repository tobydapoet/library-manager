package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Integer> {
    Optional<BookCategory> findByBook_IdAndCategory_Id(String book_id, Integer category_id);

    void deleteByBook_IdAndCategory_Id(String book_id, Integer category_id);

}
