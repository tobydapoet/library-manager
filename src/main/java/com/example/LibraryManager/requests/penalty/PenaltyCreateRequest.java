package com.example.LibraryManager.requests.penalty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PenaltyCreateRequest {
    @NotBlank
    private String borrowing_id;

    @NotNull
    private Integer quantity;

    @NotBlank
    private String bill_id;

    public String getBorrowing_id() {
        return borrowing_id;
    }

    public void setBorrowing_id(String borrowing_id) {
        this.borrowing_id = borrowing_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }
}
