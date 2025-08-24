package com.example.LibraryManager.requests.book;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

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


    public String getTitle() {
        return title;
    }

    public MultipartFile file;

    public void setTitle(String title) {
        this.title = title;
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

    public int getBorrow_price() {
        return borrow_price;
    }

    public void setBorrow_price(int borrow_price) {
        this.borrow_price = borrow_price;
    }

    public int getSell_price() {
        return sell_price;
    }

    public void setSell_price(int sell_price) {
        this.sell_price = sell_price;
    }

    public void setBorrow_price(Integer borrow_price) {
        this.borrow_price = borrow_price;
    }

    public void setSell_price(Integer sell_price) {
        this.sell_price = sell_price;
    }

    public Integer getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Integer location_id) {
        this.location_id = location_id;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Integer getImport_price() {
        return import_price;
    }

    public void setImport_price(Integer import_price) {
        this.import_price = import_price;
    }
}
