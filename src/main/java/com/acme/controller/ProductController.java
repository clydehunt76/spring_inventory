package com.acme.controller;

import com.acme.model.Product;
import com.acme.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    @Autowired
    ProductRepository repository;

    @GetMapping(path = "")
    public Iterable<Product> listProducts() {
        return repository.findAll();
    }

    @PostMapping(path = "")
    public Product createProduct(@RequestBody Product product) {
        Product saved = repository.save(product);
        return saved;
    }

    @PutMapping(path = "/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        if (productId == null || !productId.equals(product.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product saved = repository.save(product);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        repository.delete(productId);
    }

    @RequestMapping("/{productId}")
    public Iterable<Product> products(@PathVariable Long productId) {
        return repository.findById(productId);
    }

}