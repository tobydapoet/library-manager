package com.example.LibraryManager.requests.supplier;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SupplierCreateRequest {
    @NotBlank
    private String name;

    @Pattern(regexp = "^(0[35789][0-9]{8})$",
            message = "Invalid phone number!"
    )
    private String contact;

    @NotBlank
    private String address;

}


