package com.acme.controller;

import com.acme.model.Customer;
import com.acme.repository.CustomerRepository;
import com.acme.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class HelloController {

    @Autowired
    CustomerRepository repository;

    @Autowired
    HelloService helloService;

    @RequestMapping(path="/customers", method = RequestMethod.GET)
    public Iterable<Customer> customers() {
        return repository.findAll();
    }

    @RequestMapping(path="/customers", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Customer createCustomer(@RequestBody Customer customer) {
        Customer saved = repository.save(customer);
        return saved;
    }

    @RequestMapping(path="/customers/{customerId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long customerId, @RequestBody Customer customer) {
        if(customerId == null || !customerId.equals(customer.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Customer saved = repository.save(customer);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @RequestMapping(path="/customers/{customerId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long customerId) {
        repository.delete(customerId);
    }

    @RequestMapping("/customers/{customerId}")
    public Iterable<Customer> customers(@PathVariable Long customerId) {
        return repository.findById(customerId);
    }

}