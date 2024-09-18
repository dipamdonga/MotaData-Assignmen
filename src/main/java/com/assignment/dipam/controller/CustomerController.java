package com.assignment.dipam.controller;

import com.assignment.dipam.entity.CustomerBO;
import com.assignment.dipam.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerBO> createCustomer(@RequestBody CustomerBO customer) {
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CustomerBO>> getAllCustomers() {
        return new ResponseEntity<>(customerService.findAllCustomer(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerBO> getCustomerById(@PathVariable("id") String id) {
        return new ResponseEntity<>(customerService.findCustomerById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CustomerBO> updateCustomer(@RequestBody CustomerBO customerDetails) {
        return new ResponseEntity<>(customerService.updateCustomer(customerDetails), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") String id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
