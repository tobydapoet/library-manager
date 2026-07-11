package com.example.LibraryManager.dtos.responses;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class BillImportResponse {
    String id;
    SupplierResponse supplier;
    Integer total;
    boolean paid;
    Date createdAt;
}
