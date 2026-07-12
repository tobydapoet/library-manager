package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Integer> {

}
