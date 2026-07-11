package com.example.LibraryManager.dtos.requests;

import com.example.LibraryManager.enums.BookStatus;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BookUpdateRequest {
    private String title;
    private String description;
    private String author;
    private Integer borrow_price;
    private Integer sell_price;
    private Integer import_price;
    private Integer location_id;
    private MultipartFile file;
    private BookStatus status;
    private Integer quantity;

}
