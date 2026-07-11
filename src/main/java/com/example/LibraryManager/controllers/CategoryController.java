package com.example.LibraryManager.controllers;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.Category;
import com.example.LibraryManager.requests.category.CategoryRequest;
import com.example.LibraryManager.services.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping()
    public Iterable<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/book/{id}")
    public List<Category> findByBookId(@PathVariable String id) {
        return categoryService.findByBookId(id);
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Integer id) {
        return categoryService.findById(id);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping()
    public Category addCategory(@RequestBody CategoryRequest req) {
        return categoryService.create(req);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PatchMapping("/{id}")
    public Category updateCategory(@PathVariable Integer id, @RequestBody CategoryRequest req) {
        return categoryService.update(id, req);
    }


    @PreAuthorize("hasRole('STAFF')")
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoryService.delete(id);
    }
}
