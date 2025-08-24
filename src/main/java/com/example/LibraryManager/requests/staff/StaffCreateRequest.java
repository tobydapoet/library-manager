package com.example.LibraryManager.requests.staff;

import com.example.LibraryManager.enums.StaffPosition;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class StaffCreateRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    private StaffPosition position;

    private Integer salary;

    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public StaffCreateRequest() {
    }

    public StaffCreateRequest(String name, String phone, StaffPosition position, Integer salary) {
        this.name = name;
        this.phone = phone;
        this.position = position;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public StaffPosition getPosition() {
        return position;
    }

    public void setPosition(StaffPosition position) {
        this.position = position;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }


}
