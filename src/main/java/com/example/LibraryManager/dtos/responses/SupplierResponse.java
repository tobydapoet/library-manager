package com.example.LibraryManager.dtos.responses;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SupplierResponse {
    String id;
    String name;
    String contact;
    String address;
    boolean deleted;
}
