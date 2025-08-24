package com.example.LibraryManager.requests.purchase;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PurchaseCreateRequest {
    @NotBlank
    private String book_id;

    @NotBlank
    private String supplier_id;


    @NotNull
    private Integer quantity;

    @NotBlank
    private String billImport_id;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getBillImport_id() {
        return billImport_id;
    }

    public void setBillImport_id(String billImport_id) {
        this.billImport_id = billImport_id;
    }
}
