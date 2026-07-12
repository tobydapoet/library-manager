package com.example.LibraryManager.services;

import com.example.LibraryManager.entities.Book;
import com.example.LibraryManager.enums.BookStatus;
import com.example.LibraryManager.exception.BadRequestException;
import com.example.LibraryManager.exception.ResourceNotFoundException;
import com.example.LibraryManager.repositories.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookService unit tests")
class BookServiceTest {
    @Mock BookRepository bookRepository;
    @Mock UploadService uploadService;
    @Mock LocationService locationService;
    @InjectMocks BookService bookService;

    @Nested
    @DisplayName("decreaseStock")
    class DecreaseStock {
        @Test
        @DisplayName("should decrease quantity and mark the book as borrowed when stock reaches zero")
        void shouldDecreaseStockAndUpdateStatus() {
            Book book = book("book-1", 2, BookStatus.available);
            when(bookRepository.findByIdForUpdate("book-1")).thenReturn(Optional.of(book));
            when(bookRepository.save(book)).thenReturn(book);

            Book result = bookService.decreaseStock("book-1", 2);

            assertEquals(0, result.getQuantity());
            assertEquals(BookStatus.borrowed, result.getStatus());
            verify(bookRepository).save(book);
        }

        @Test
        @DisplayName("should reject a quantity greater than available stock")
        void shouldRejectInsufficientStock() {
            Book book = book("book-1", 1, BookStatus.available);
            when(bookRepository.findByIdForUpdate("book-1")).thenReturn(Optional.of(book));

            assertThrows(BadRequestException.class,
                    () -> bookService.decreaseStock("book-1", 2));
            verify(bookRepository, never()).save(any());
        }

        @Test
        @DisplayName("should reject non-positive quantity")
        void shouldRejectNonPositiveQuantity() {
            assertThrows(BadRequestException.class,
                    () -> bookService.decreaseStock("book-1", 0));
            verifyNoInteractions(bookRepository);
        }
    }

    @Nested
    @DisplayName("increaseStock")
    class IncreaseStock {
        @Test
        @DisplayName("should increase quantity and make a borrowed book available")
        void shouldIncreaseStockAndUpdateStatus() {
            Book book = book("book-1", 0, BookStatus.borrowed);
            when(bookRepository.findByIdForUpdate("book-1")).thenReturn(Optional.of(book));
            when(bookRepository.save(book)).thenReturn(book);

            Book result = bookService.increaseStock("book-1", 3);

            assertEquals(3, result.getQuantity());
            assertEquals(BookStatus.available, result.getStatus());
        }

        @Test
        @DisplayName("should throw when the book does not exist")
        void shouldThrowWhenBookDoesNotExist() {
            when(bookRepository.findByIdForUpdate("missing")).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class,
                    () -> bookService.increaseStock("missing", 1));
        }
    }

    private Book book(String id, int quantity, BookStatus status) {
        Book book = new Book();
        book.setId(id);
        book.setQuantity(quantity);
        book.setStatus(status);
        return book;
    }
}
