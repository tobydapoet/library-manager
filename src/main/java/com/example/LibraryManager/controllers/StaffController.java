package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.dtos.requests.StaffUpdateRequest;
import com.example.LibraryManager.dtos.responses.StaffResponse;
import com.example.LibraryManager.mappers.StaffMapper;
import com.example.LibraryManager.services.StaffService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;
    private final StaffMapper staffMapper;

    @PreAuthorize("hasAuthority('POSITION_ADMIN')")
    @GetMapping("/search")
    public java.util.List<StaffResponse> searchClient(@RequestParam String keyword) {
        return staffMapper.toResponses(staffService.searchStaff(keyword));
    }

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/{id}")
    public StaffResponse getStaff(@PathVariable String id) {
        return staffMapper.toResponse(staffService.getStaff(id));
    }

    @PreAuthorize("hasAuthority('POSITION_ADMIN')")
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StaffResponse update(
            @PathVariable String id,
            @RequestPart("dto") StaffUpdateRequest req) {
        return staffMapper.toResponse(staffService.update(id, req));
    }
}
