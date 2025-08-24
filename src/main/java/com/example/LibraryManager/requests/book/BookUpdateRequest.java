package com.example.LibraryManager.requests.book;

import com.example.LibraryManager.enums.BookStatus;
import org.springframework.web.multipart.MultipartFile;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Integer location_id) {
        this.location_id = location_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getBorrow_price() {
        return borrow_price;
    }

    public void setBorrow_price(Integer borrow_price) {
        this.borrow_price = borrow_price;
    }

    public Integer getSell_price() {
        return sell_price;
    }

    public void setSell_price(Integer sell_price) {
        this.sell_price = sell_price;
    }


    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public Integer getImport_price() {
        return import_price;
    }

    public void setImport_price(Integer import_price) {
        this.import_price = import_price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
