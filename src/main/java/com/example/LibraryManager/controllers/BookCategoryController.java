package com.example.LibraryManager.controllers;

import com.example.LibraryManager.entities.BookCategory;
import com.example.LibraryManager.requests.bookcategory.BookCategoryRequest;
import com.example.LibraryManager.services.BookCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book_category")
public class BookCategoryController {
    @Autowired
    private BookCategoryService bookCategoryService;

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping()
    private BookCategory create(BookCategoryRequest req) {
        return bookCategoryService.create(req);
    }

    @PreAuthorize("hasRole('STAFF')")
    @DeleteMapping("/{id}")
    private void delete(int id) {
        bookCategoryService.deleteById(id);
    }
}
