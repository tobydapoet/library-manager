package com.example.LibraryManager.services;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.Book;
import com.example.LibraryManager.entities.BookCategory;
import com.example.LibraryManager.entities.Category;
import com.example.LibraryManager.repositories.BookCategoryRepository;
import com.example.LibraryManager.dtos.requests.BookCategoryRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookCategoryService {
    private final BookCategoryRepository bookCategoryRepository;

    private final BookService bookService;

    private final CategoryService categoryService;

    public BookCategory create(BookCategoryRequest req) {
        BookCategory bookCategory = new BookCategory();
        Book book = bookService.findById(req.getBook_id());
        bookCategory.setBook(book);
        Category category = categoryService.findById(req.getCategory_id());
        bookCategory.setCategory(category);
        return bookCategoryRepository.save(bookCategory);
    }

    public void deleteById(int id) {
        bookCategoryRepository.deleteById(id);
    }


}
