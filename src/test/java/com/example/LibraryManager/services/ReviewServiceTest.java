package com.example.LibraryManager.services;

import com.example.LibraryManager.dtos.requests.ReviewCreateRequest;
import com.example.LibraryManager.entities.Book;
import com.example.LibraryManager.entities.Client;
import com.example.LibraryManager.entities.Review;
import com.example.LibraryManager.exception.DuplicateResourceException;
import com.example.LibraryManager.repositories.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReviewService unit tests")
class ReviewServiceTest {
    @Mock ReviewRepository reviewRepository;
    @Mock BookService bookService;
    @Mock ClientService clientService;
    @InjectMocks ReviewService reviewService;

    @Nested
    @DisplayName("create")
    class Create {
        @Test
        @DisplayName("should create a review with the requested rating")
        void shouldCreateReviewWithRating() {
            ReviewCreateRequest request = request();
            when(reviewRepository.existsByClientAndBook("client-1", "book-1")).thenReturn(false);
            when(clientService.getClient("client-1")).thenReturn(new Client());
            when(bookService.findById("book-1")).thenReturn(new Book());
            when(reviewRepository.save(any(Review.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            Review result = reviewService.create(request);

            assertEquals(5, result.getRating());
            assertEquals("Excellent", result.getComment());
        }

        @Test
        @DisplayName("should reject a second review from the same client for the same book")
        void shouldRejectDuplicateReview() {
            ReviewCreateRequest request = request();
            when(reviewRepository.existsByClientAndBook("client-1", "book-1")).thenReturn(true);

            assertThrows(DuplicateResourceException.class,
                    () -> reviewService.create(request));
            verify(reviewRepository, never()).save(any());
            verifyNoInteractions(bookService, clientService);
        }
    }

    private ReviewCreateRequest request() {
        ReviewCreateRequest request = new ReviewCreateRequest();
        request.setClientId("client-1");
        request.setBookId("book-1");
        request.setRating(5);
        request.setComment("Excellent");
        return request;
    }
}
