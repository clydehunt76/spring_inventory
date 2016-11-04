package com.acme.controller;

import com.acme.model.Category;
import com.acme.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/categories")
public class CategoryController {

    @Autowired
    CategoryRepository repository;

    @GetMapping(path = "")
    public Iterable<Category> listCategories() {
        return repository.findAll();
    }

    @PostMapping(path = "")
    public Category createCategory(@RequestBody Category category) {
        return repository.save(category);
    }

    @PutMapping(path = "/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        if (categoryId == null || !categoryId.equals(category.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Category saved = repository.save(category);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        repository.delete(categoryId);
    }

    @GetMapping(path = "/{categoryId}")
    public Iterable<Category> categories(@PathVariable Long categoryId) {
        return repository.findById(categoryId);
    }

}