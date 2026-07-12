package com.example.LibraryManager.services;

import com.example.LibraryManager.entities.Location;
import com.example.LibraryManager.repositories.LocationRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("LocationService unit tests")
class LocationServiceTest {
    @Mock LocationRepository locationRepository;
    @InjectMocks LocationService locationService;

    @Nested @DisplayName("update")
    class Update {
        @Test @DisplayName("should update the floor number")
        void shouldUpdateFloor() {
            Location location = new Location(); location.setFloor(1);
            when(locationRepository.findById(1)).thenReturn(Optional.of(location));
            when(locationRepository.save(location)).thenReturn(location);

            Location result = locationService.update(1, 3);

            assertEquals(3, result.getFloor());
        }
    }
}
