package com.example.LibraryManager.services;

import com.example.LibraryManager.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.Category;
import com.example.LibraryManager.repositories.CategoryRepository;
import com.example.LibraryManager.dtos.requests.CategoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    public List<Category> findByBookId(String bookId) {
        return categoryRepository.findByBookId(bookId);
    }

    public Category create(CategoryRequest req) {
        Category category = new Category();
        category.setName(req.getName());
        return categoryRepository.save(category);
    }

    public Category update(Integer id, CategoryRequest req) {
        Category category = findById(id);
        category.setName(req.getName());
        return categoryRepository.save(category);
    }

    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }
}
