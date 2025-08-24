package com.example.LibraryManager.controllers;

import com.example.LibraryManager.entities.Location;
import com.example.LibraryManager.requests.location.LocationRequest;
import com.example.LibraryManager.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    LocationService locationService;

    @GetMapping()
    public Iterable<Location> findAll() {
        return locationService.findAll();
    }

    @GetMapping("/{id}")
    public Location getLocationById(@PathVariable Integer id) {
        return locationService.findById(id);
    }

    @PostMapping()
    public Location createLocation(@RequestBody LocationRequest req) {
        return locationService.create(req);
    }

    @PreAuthorize("hasAnyAuthority('POSITION_LIBRARIAN','POSITION_ADMIN')")
    @PutMapping("/{id}")
    public Location updateLocation(@PathVariable Integer id, @RequestBody LocationRequest req) {
        return locationService.update(id, req.getFloor());
    }

    @PreAuthorize("hasAnyAuthority('POSITION_LIBRARIAN','POSITION_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable Integer id) {
        locationService.delete(id);
    }
}
