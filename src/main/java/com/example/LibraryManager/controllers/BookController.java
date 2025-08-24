package com.example.LibraryManager.controllers;

import com.example.LibraryManager.entities.Book;
import com.example.LibraryManager.requests.book.BookCreateRequest;
import com.example.LibraryManager.requests.book.BookUpdateRequest;
import com.example.LibraryManager.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("book")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping()
    public Page<Book> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return bookService.findAll(page, size);
    }

    @GetMapping("/category")
    public Page<Book> getBooksByCategory(
            @RequestParam String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return bookService.getBookByCategory(categoryName, page, size);
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable String id) {
        return bookService.findById(id);
    }

    @GetMapping("search")
    public Page<Book> searchBook(String type, String keyword, int page, int size) {
        return bookService.searchBook(type, keyword, page, size);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Book addBook(@ModelAttribute BookCreateRequest req) {
        return bookService.create(req);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Book updateBook(@PathVariable String id, @ModelAttribute BookUpdateRequest req) {
        return bookService.update(id, req);
    }
}
