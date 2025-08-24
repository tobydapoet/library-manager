package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.Book;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BookRepository extends JpaRepository<Book, String> {

    Page<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);

    Page<Book> findByTitleIgnoreCase(String title, Pageable pageable);

    @Query("SELECT bc.book FROM BookCategory bc WHERE bc.category.name = :categoryName")
    Page<Book> findBooksByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);
}
