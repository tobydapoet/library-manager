package com.example.LibraryManager.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.example.LibraryManager.exception.BadRequestException;
import com.example.LibraryManager.exception.FileStorageException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UploadService unit tests")
class UploadServiceTest {
    @Mock Cloudinary cloudinary;
    @Mock Uploader uploader;
    @Mock MultipartFile file;
    private UploadService uploadService;

    @BeforeEach
    void setUp() {
        uploadService = new UploadService(cloudinary);
    }

    @Nested @DisplayName("upload")
    class Upload {
        @Test @DisplayName("should return the secure URL from Cloudinary")
        void shouldReturnSecureUrl() throws IOException {
            when(cloudinary.uploader()).thenReturn(uploader);
            when(file.getBytes()).thenReturn(new byte[]{1});
            when(file.getOriginalFilename()).thenReturn("book.png");
            when(uploader.upload(any(byte[].class), anyMap()))
                    .thenReturn(Map.of("secure_url", "https://cdn.example/book.png"));

            String result = uploadService.upload(file, "book");

            assertEquals("https://cdn.example/book.png", result);
        }

        @Test @DisplayName("should wrap IO failures in FileStorageException")
        void shouldWrapIoFailure() throws IOException {
            when(cloudinary.uploader()).thenReturn(uploader);
            when(file.getBytes()).thenThrow(new IOException("read failed"));

            assertThrows(FileStorageException.class,
                    () -> uploadService.upload(file, "book"));
        }
    }

    @Nested @DisplayName("delete")
    class Delete {
        @Test @DisplayName("should reject an invalid Cloudinary URL")
        void shouldRejectInvalidUrl() throws IOException {
            assertThrows(BadRequestException.class,
                    () -> uploadService.delete("https://example.com/file.png"));
            verify(uploader, never()).destroy(anyString(), anyMap());
        }
    }
}
