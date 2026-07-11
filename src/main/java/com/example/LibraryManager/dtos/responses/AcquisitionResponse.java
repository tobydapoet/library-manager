package com.example.LibraryManager.dtos.responses;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class AcquisitionResponse {
    String id;
    ClientResponse client;
    BookResponse book;
    Integer quantity;
    int price;
    String billId;
    Date createdAt;
}
