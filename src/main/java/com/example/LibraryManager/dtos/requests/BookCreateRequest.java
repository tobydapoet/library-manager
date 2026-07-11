package com.example.LibraryManager.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BookCreateRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String author;

    @NotBlank
    private Integer borrow_price;

    private Integer sell_price;

    private Integer import_price;

    private Integer location_id;


    public MultipartFile file;
}
