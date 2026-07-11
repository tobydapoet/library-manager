package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.dtos.requests.ReviewCreateRequest;
import com.example.LibraryManager.dtos.responses.ReviewResponse;
import com.example.LibraryManager.mappers.ReviewMapper;
import com.example.LibraryManager.services.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @GetMapping("/book")
    public Page<ReviewResponse> findByBookId(
            @RequestParam String bookId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return reviewService.findByBookId(bookId, page, size).map(reviewMapper::toResponse);
    }

    @GetMapping("/{id}")
    public ReviewResponse findById(@PathVariable String id) {
        return reviewMapper.toResponse(reviewService.findById(id));
    }

    @PostMapping()
    public ReviewResponse create(@RequestBody ReviewCreateRequest req) {
        return reviewMapper.toResponse(reviewService.create(req));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        reviewService.delete(id);
    }
}
