package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.dtos.requests.BookCreateRequest;
import com.example.LibraryManager.dtos.requests.BookUpdateRequest;
import com.example.LibraryManager.dtos.responses.BookResponse;
import com.example.LibraryManager.mappers.BookMapper;
import com.example.LibraryManager.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping()
    public Page<BookResponse> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return bookService.findAll(page, size).map(bookMapper::toResponse);
    }

    @GetMapping("/category")
    public Page<BookResponse> getBooksByCategory(
            @RequestParam String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return bookService.getBookByCategory(categoryName, page, size).map(bookMapper::toResponse);
    }

    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable String id) {
        return bookMapper.toResponse(bookService.findById(id));
    }

    @GetMapping("search")
    public Page<BookResponse> searchBook(String type, String keyword, int page, int size) {
        return bookService.searchBook(type, keyword, page, size).map(bookMapper::toResponse);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BookResponse addBook(@ModelAttribute BookCreateRequest req) {
        return bookMapper.toResponse(bookService.create(req));
    }

    @PreAuthorize("hasRole('STAFF')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BookResponse updateBook(@PathVariable String id, @ModelAttribute BookUpdateRequest req) {
        return bookMapper.toResponse(bookService.update(id, req));
    }
}
