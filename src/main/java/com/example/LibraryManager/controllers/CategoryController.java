package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.dtos.requests.CategoryRequest;
import com.example.LibraryManager.dtos.responses.CategoryResponse;
import com.example.LibraryManager.mappers.CategoryMapper;
import com.example.LibraryManager.services.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping()
    public List<CategoryResponse> getAllCategories() {
        return categoryMapper.toResponses(categoryService.findAll());
    }

    @GetMapping("/book/{id}")
    public List<CategoryResponse> findByBookId(@PathVariable String id) {
        return categoryService.findByBookId(id).stream().map(categoryMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Integer id) {
        return categoryMapper.toResponse(categoryService.findById(id));
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping()
    public CategoryResponse addCategory(@RequestBody CategoryRequest req) {
        return categoryMapper.toResponse(categoryService.create(req));
    }

    @PreAuthorize("hasRole('STAFF')")
    @PatchMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable Integer id, @RequestBody CategoryRequest req) {
        return categoryMapper.toResponse(categoryService.update(id, req));
    }


    @PreAuthorize("hasRole('STAFF')")
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoryService.delete(id);
    }
}
