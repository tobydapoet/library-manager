package com.example.LibraryManager.controllers;

import com.example.LibraryManager.entities.Review;
import com.example.LibraryManager.requests.review.ReviewCreateRequest;
import com.example.LibraryManager.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/book")
    public Page<Review> findByBookId(
            @RequestParam String bookId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return reviewService.findByBookId(bookId, page, size);
    }

    @GetMapping("/{id}")
    public Review findById(@PathVariable String id) {
        return reviewService.findById(id);
    }

    @PostMapping()
    public Review create(@RequestBody ReviewCreateRequest req) {
        return reviewService.create(req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        reviewService.delete(id);
    }
}
