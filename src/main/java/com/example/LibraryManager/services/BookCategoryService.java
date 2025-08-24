package com.example.LibraryManager.services;

import com.example.LibraryManager.entities.Book;
import com.example.LibraryManager.entities.BookCategory;
import com.example.LibraryManager.entities.Category;
import com.example.LibraryManager.repositories.BookCategoryRepository;
import com.example.LibraryManager.requests.bookcategory.BookCategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookCategoryService {
    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

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
