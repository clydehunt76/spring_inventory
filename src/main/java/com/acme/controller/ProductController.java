package com.acme.controller;

import com.acme.model.Product;
import com.acme.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    ProductRepository repository;

    @RequestMapping(path = "/products", method = RequestMethod.GET)
    public Iterable<Product> listProducts() {
        return repository.findAll();
    }

    @RequestMapping(path = "/products", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Product createProduct(@RequestBody Product product) {
        Product saved = repository.save(product);
        return saved;
    }

    @RequestMapping(path = "/products/{productId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        if (productId == null || !productId.equals(product.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product saved = repository.save(product);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @RequestMapping(path = "/products/{productId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Long productId) {
        repository.delete(productId);
    }

    @RequestMapping("/products/{productId}")
    public Iterable<Product> products(@PathVariable Long productId) {
        return repository.findById(productId);
    }

}