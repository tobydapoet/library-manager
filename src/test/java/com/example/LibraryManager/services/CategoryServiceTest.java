package com.example.LibraryManager.services;

import com.example.LibraryManager.dtos.requests.CategoryRequest;
import com.example.LibraryManager.entities.Category;
import com.example.LibraryManager.exception.ResourceNotFoundException;
import com.example.LibraryManager.repositories.CategoryRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CategoryService unit tests")
class CategoryServiceTest {
    @Mock CategoryRepository categoryRepository;
    @InjectMocks CategoryService categoryService;

    @Nested @DisplayName("findById")
    class FindById {
        @Test @DisplayName("should throw when the category does not exist")
        void shouldThrowWhenMissing() {
            when(categoryRepository.findById(99)).thenReturn(Optional.empty());
            assertThrows(ResourceNotFoundException.class, () -> categoryService.findById(99));
        }
    }

    @Nested @DisplayName("update")
    class Update {
        @Test @DisplayName("should update the category name")
        void shouldUpdateName() {
            Category category = new Category(); category.setName("Old");
            CategoryRequest request = new CategoryRequest(); request.setName("New");
            when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
            when(categoryRepository.save(category)).thenReturn(category);

            Category result = categoryService.update(1, request);

            assertEquals("New", result.getName());
        }
    }
}
