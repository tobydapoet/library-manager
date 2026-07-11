package com.example.LibraryManager.dtos.responses;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class ReviewResponse {
    String id;
    ClientResponse client;
    BookResponse book;
    int rating;
    String comment;
    Date createdAt;
}
