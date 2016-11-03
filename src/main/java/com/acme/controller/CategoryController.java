package com.acme.controller;

import com.acme.model.Category;
import com.acme.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {

    @Autowired
    CategoryRepository repository;

    @RequestMapping(path = "/categories", method = RequestMethod.GET)
    public Iterable<Category> listCategories() {
        return repository.findAll();
    }

    @RequestMapping(path = "/categories", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Category createCategory(@RequestBody Category category) {
        Category saved = repository.save(category);
        return saved;
    }

    @RequestMapping(path = "/categories/{categoryId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        if (categoryId == null || !categoryId.equals(category.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Category saved = repository.save(category);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @RequestMapping(path = "/categories/{categoryId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@PathVariable Long categoryId) {
        repository.delete(categoryId);
    }

    @RequestMapping("/categories/{categoryId}")
    public Iterable<Category> categories(@PathVariable Long categoryId) {
        return repository.findById(categoryId);
    }

}