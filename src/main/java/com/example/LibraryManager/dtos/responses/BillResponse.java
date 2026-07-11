package com.example.LibraryManager.dtos.responses;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class BillResponse {
    String id;
    ClientResponse client;
    Integer total;
    boolean paid;
    Date createdAt;
}
