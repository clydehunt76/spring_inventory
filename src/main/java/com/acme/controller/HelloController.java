package com.acme.controller;

import com.acme.model.Customer;
import com.acme.repository.CustomerRepository;
import com.acme.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @Autowired
    CustomerRepository repository;

    @Autowired
    HelloService helloService;

    @RequestMapping("/customers")
    public Iterable<Customer> customers() {
        return repository.findAll();
    }

    @RequestMapping("/hello")
    public String hello() {
        return helloService.getMessage();
    }

}