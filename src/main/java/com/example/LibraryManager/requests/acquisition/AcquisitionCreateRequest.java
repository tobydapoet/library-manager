package com.example.LibraryManager.requests.acquisition;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AcquisitionCreateRequest {
    @NotBlank
    private String client_id;

    @NotBlank
    private String book_id;

    private Integer quantity;

    @NotNull
    private int price;

    @NotBlank
    private String bill_id;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }
}
