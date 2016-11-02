package com.acme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @Autowired
    CustomerRepository repository;

    @RequestMapping("/")
    public Iterable<Customer> index() {
        return repository.findAll();
    }

}