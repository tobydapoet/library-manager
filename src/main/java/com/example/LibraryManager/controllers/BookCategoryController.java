package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.dtos.requests.BookCategoryRequest;
import com.example.LibraryManager.dtos.responses.BookCategoryResponse;
import com.example.LibraryManager.mappers.BookCategoryMapper;
import com.example.LibraryManager.services.BookCategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book_category")
@RequiredArgsConstructor
public class BookCategoryController {
    private final BookCategoryService bookCategoryService;
    private final BookCategoryMapper bookCategoryMapper;

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping()
    private BookCategoryResponse create(BookCategoryRequest req) {
        return bookCategoryMapper.toResponse(bookCategoryService.create(req));
    }

    @PreAuthorize("hasRole('STAFF')")
    @DeleteMapping("/{id}")
    private void delete(int id) {
        bookCategoryService.deleteById(id);
    }
}
