package com.example.LibraryManager.repositories;

import com.example.LibraryManager.entities.Book;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Book b WHERE b.id = :id")
    Optional<Book> findByIdForUpdate(@Param("id") String id);

    Page<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);

    Page<Book> findByTitleIgnoreCase(String title, Pageable pageable);

    @Query("SELECT bc.book FROM BookCategory bc WHERE bc.category.name = :categoryName")
    Page<Book> findBooksByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);
}
