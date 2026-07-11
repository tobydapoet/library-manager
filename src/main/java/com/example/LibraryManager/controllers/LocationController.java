package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.dtos.requests.LocationRequest;
import com.example.LibraryManager.dtos.responses.LocationResponse;
import com.example.LibraryManager.mappers.LocationMapper;
import com.example.LibraryManager.services.LocationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    private final LocationMapper locationMapper;

    @GetMapping()
    public java.util.List<LocationResponse> findAll() {
        return locationMapper.toResponses(locationService.findAll());
    }

    @GetMapping("/{id}")
    public LocationResponse getLocationById(@PathVariable Integer id) {
        return locationMapper.toResponse(locationService.findById(id));
    }

    @PostMapping()
    public LocationResponse createLocation(@RequestBody LocationRequest req) {
        return locationMapper.toResponse(locationService.create(req));
    }

    @PreAuthorize("hasAnyAuthority('POSITION_LIBRARIAN','POSITION_ADMIN')")
    @PutMapping("/{id}")
    public LocationResponse updateLocation(@PathVariable Integer id, @RequestBody LocationRequest req) {
        return locationMapper.toResponse(locationService.update(id, req.getFloor()));
    }

    @PreAuthorize("hasAnyAuthority('POSITION_LIBRARIAN','POSITION_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable Integer id) {
        locationService.delete(id);
    }
}
