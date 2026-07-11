package com.example.LibraryManager.services;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.Staff;
import com.example.LibraryManager.entities.User;
import com.example.LibraryManager.repositories.StaffRepository;
import com.example.LibraryManager.dtos.requests.StaffCreateRequest;
import com.example.LibraryManager.dtos.requests.StaffUpdateRequest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;

    private final UploadService uploadService;

    public Staff getStaff(String id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));
    }

    public Iterable<Staff> getStaffs() {
        return staffRepository.findAll();
    }

    @Cacheable
    public Iterable<Staff> searchStaff(String keyword) {
        return staffRepository.findByPhoneContainingIgnoreCaseOrNameContainingIgnoreCaseOrUserEmailContainingIgnoreCase(keyword, keyword, keyword);
    }

    public Staff create(StaffCreateRequest req, User user) {
        Staff staff = new Staff();
        staff.setName(req.getName());
        staff.setPhone(req.getPhone());
        staff.setPosition(req.getPosition());
        if (req.getSalary() != null) {
            staff.setSalary(req.getSalary());
        }
        String savedAvatar = uploadService.upload(req.getFile(), "staff");
        staff.setAvatar_url(savedAvatar);
        staff.setUser(user);
        return staffRepository.save(staff);
    }

    public Staff update(String id, StaffUpdateRequest req) {
        Staff staff = getStaff(id);
        if (req.getName() != null) staff.setName(req.getName());
        if (req.getPhone() != null) staff.setPhone(req.getPhone());
        if (req.getSalary() != null) staff.setSalary(req.getSalary());
        if (req.getPhone() != null) staff.setPhone(req.getPhone());
        if (req.getFile() != null) {
            if (staff.getAvatar_url() != null) {
                uploadService.delete(staff.getAvatar_url());
            }
            String uploadedFile = uploadService.upload(req.getFile(), "staff");
            staff.setAvatar_url(uploadedFile);
        }
        if (req.isActive()) staff.setActive(req.isActive());
        return staffRepository.save(staff);
    }
}
