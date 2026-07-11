package com.example.LibraryManager.mappers;

import com.example.LibraryManager.dtos.responses.LocationResponse;
import com.example.LibraryManager.entities.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper implements EntityMapper<Location, LocationResponse> {
    @Override
    public LocationResponse toResponse(Location entity) {
        if (entity == null) return null;
        return LocationResponse.builder().id(entity.getId()).floor(entity.getFloor()).build();
    }
}
