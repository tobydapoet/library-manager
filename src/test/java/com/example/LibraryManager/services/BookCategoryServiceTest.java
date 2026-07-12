package com.example.LibraryManager.services;

import com.example.LibraryManager.dtos.requests.BookCategoryRequest;
import com.example.LibraryManager.entities.*;
import com.example.LibraryManager.repositories.BookCategoryRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookCategoryService unit tests")
class BookCategoryServiceTest {
    @Mock BookCategoryRepository bookCategoryRepository;
    @Mock BookService bookService;
    @Mock CategoryService categoryService;
    @InjectMocks BookCategoryService bookCategoryService;

    @Nested @DisplayName("create")
    class Create {
        @Test @DisplayName("should link the selected book and category")
        void shouldLinkBookAndCategory() {
            BookCategoryRequest request = new BookCategoryRequest();
            request.setBook_id("book-1"); request.setCategory_id(2);
            Book book = new Book(); Category category = new Category();
            when(bookService.findById("book-1")).thenReturn(book);
            when(categoryService.findById(2)).thenReturn(category);
            when(bookCategoryRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

            BookCategory result = bookCategoryService.create(request);

            assertSame(book, result.getBook());
            assertSame(category, result.getCategory());
        }
    }
}
