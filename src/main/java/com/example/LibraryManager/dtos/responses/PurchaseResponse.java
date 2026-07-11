package com.example.LibraryManager.dtos.responses;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class PurchaseResponse {
    String id;
    BookResponse book;
    SupplierResponse supplier;
    int price;
    int quantity;
    String billImportId;
    Date createdAt;
}
