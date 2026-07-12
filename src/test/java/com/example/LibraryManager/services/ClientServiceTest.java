package com.example.LibraryManager.services;

import com.example.LibraryManager.dtos.requests.ClientUpdateRequest;
import com.example.LibraryManager.entities.Client;
import com.example.LibraryManager.repositories.ClientRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClientService unit tests")
class ClientServiceTest {
    @Mock ClientRepository clientRepository;
    @Mock UploadService uploadService;
    @InjectMocks ClientService clientService;

    @Nested @DisplayName("update")
    class Update {
        @Test @DisplayName("should replace the existing avatar exactly once")
        void shouldReplaceAvatarOnce() {
            Client client = new Client(); client.setId("client-1"); client.setAvatar_url("old-url");
            MultipartFile file = mock(MultipartFile.class);
            ClientUpdateRequest request = new ClientUpdateRequest(); request.setFile(file);
            when(file.isEmpty()).thenReturn(false);
            when(clientRepository.findById("client-1")).thenReturn(Optional.of(client));
            when(uploadService.upload(file, "client")).thenReturn("new-url");
            when(clientRepository.save(client)).thenReturn(client);

            Client result = clientService.update("client-1", request);

            assertEquals("new-url", result.getAvatar_url());
            verify(uploadService, times(1)).upload(file, "client");
            verify(uploadService).delete("old-url");
        }
    }
}
