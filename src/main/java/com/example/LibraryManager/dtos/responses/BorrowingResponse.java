package com.example.LibraryManager.dtos.responses;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class BorrowingResponse {
    String id;
    ClientResponse client;
    BookResponse book;
    int price;
    int quantity;
    Date expiryDate;
    Date createdAt;
    String billId;
}
