package com.example.LibraryManager.requests.staff;

import com.example.LibraryManager.enums.StaffPosition;
import org.springframework.web.multipart.MultipartFile;

public class StaffUpdateRequest {
    private String name;
    private String phone;
    private StaffPosition position;
    private Integer salary;
    private boolean isActive;
    private String avatar_url;
    private MultipartFile file;

    public StaffUpdateRequest(String name, String phone, StaffPosition position, Integer salary, boolean isActive, String avatar_url, MultipartFile file) {
        this.name = name;
        this.phone = phone;
        this.position = position;
        this.salary = salary;
        this.isActive = isActive;
        this.avatar_url = avatar_url;
        this.file = file;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
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


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
