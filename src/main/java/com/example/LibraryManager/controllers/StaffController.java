package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.Staff;
import com.example.LibraryManager.requests.staff.StaffUpdateRequest;
import com.example.LibraryManager.services.StaffService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;

    @PreAuthorize("hasAuthority('POSITION_ADMIN')")
    @GetMapping("/search")
    public Iterable<Staff> searchClient(@RequestParam String keyword) {
        return staffService.searchStaff(keyword);
    }

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/{id}")
    public Staff getStaff(@PathVariable String id) {
        return staffService.getStaff(id);
    }

    @PreAuthorize("hasAuthority('POSITION_ADMIN')")
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Staff update(
            @PathVariable String id,
            @RequestPart("dto") StaffUpdateRequest req) {
        return staffService.update(id, req);
    }
}
