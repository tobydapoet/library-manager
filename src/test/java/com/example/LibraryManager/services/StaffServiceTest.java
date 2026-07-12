package com.example.LibraryManager.services;

import com.example.LibraryManager.dtos.requests.StaffUpdateRequest;
import com.example.LibraryManager.entities.Staff;
import com.example.LibraryManager.enums.StaffPosition;
import com.example.LibraryManager.repositories.StaffRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("StaffService unit tests")
class StaffServiceTest {
    @Mock StaffRepository staffRepository;
    @Mock UploadService uploadService;
    @InjectMocks StaffService staffService;

    @Nested @DisplayName("update")
    class Update {
        @Test @DisplayName("should allow deactivating a staff member and changing position")
        void shouldDeactivateAndChangePosition() {
            Staff staff = new Staff(); staff.setActive(true);
            StaffUpdateRequest request = new StaffUpdateRequest();
            request.setActive(false); request.setPosition(StaffPosition.admin);
            when(staffRepository.findById("staff-1")).thenReturn(Optional.of(staff));
            when(staffRepository.save(staff)).thenReturn(staff);

            Staff result = staffService.update("staff-1", request);

            assertFalse(result.isActive());
            assertEquals(StaffPosition.admin, result.getPosition());
        }
    }
}
