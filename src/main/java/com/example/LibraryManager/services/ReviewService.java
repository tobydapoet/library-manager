package com.example.LibraryManager.services;

import com.example.LibraryManager.dtos.requests.ReviewCreateRequest;
import com.example.LibraryManager.entities.Book;
import com.example.LibraryManager.entities.Client;
import com.example.LibraryManager.entities.Review;
import com.example.LibraryManager.exception.DuplicateResourceException;
import com.example.LibraryManager.exception.ResourceNotFoundException;
import com.example.LibraryManager.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    private final BookService bookService;

    private final ClientService clientService;

    @Cacheable(value = "uploadCache", key = "'review:' + #bookId")
    public Page<Review> findByBookId(String bookId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return reviewRepository.findByBook_Id(bookId, pageable);
    }

    public Review findById(String id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review Not Found!"));
    }

    @CacheEvict(value = "uploadCache", allEntries = true)
    public Review create(ReviewCreateRequest req) {
        if (reviewRepository.existsByClientAndBook(req.getClientId(), req.getBookId())) {
            throw new DuplicateResourceException("Client has already reviewed this book");
        }
        Review review = new Review();
        review.setComment(req.getComment());
        Client client = clientService.getClient(req.getClientId());
        review.setClient(client);
        Book book = bookService.findById(req.getBookId());
        review.setBook(book);
        review.setRating(req.getRating());
        return reviewRepository.save(review);
    }

    @CacheEvict(value = "uploadCache", allEntries = true)
    public void delete(String reviewId) {
        Review review = findById(reviewId);
        reviewRepository.delete(review);
    }
}
