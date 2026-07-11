package com.example.LibraryManager.dtos.requests;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class ClientUpdateRequest {
    private String name;

    private String phone;

    private Date dob;

    private MultipartFile file;

    private String address;

}
