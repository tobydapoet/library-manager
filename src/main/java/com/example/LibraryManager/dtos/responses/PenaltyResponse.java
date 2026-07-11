package com.example.LibraryManager.dtos.responses;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class PenaltyResponse {
    String id;
    String borrowingId;
    int quantity;
    int price;
    String billId;
    Date createdAt;
}
