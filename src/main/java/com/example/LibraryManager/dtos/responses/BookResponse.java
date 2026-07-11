package com.example.LibraryManager.dtos.responses;

import com.example.LibraryManager.enums.BookStatus;
import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class BookResponse {
    String id;
    String title;
    String description;
    String imageUrl;
    String author;
    BookStatus status;
    int borrowPrice;
    int sellPrice;
    int importPrice;
    Integer quantity;
    LocationResponse location;
    Date createdAt;
}
