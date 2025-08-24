package com.example.LibraryManager.requests.supplier;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SupplierCreateRequest {
    @NotBlank
    private String name;

    @Pattern(regexp = "^(0[35789][0-9]{8})$",
            message = "Invalid phone number!"
    )
    private String contact;

    @NotBlank
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}


