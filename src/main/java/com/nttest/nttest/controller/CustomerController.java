package com.nttest.nttest.controller;

import com.nttest.nttest.dto.CustomerRequest;
import com.nttest.nttest.model.Customer;
import com.nttest.nttest.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> retrieveCustomer(@PathVariable String username) {
        return ResponseEntity.ok(customerService.findByUsername(username));
    }

    @GetMapping
    public ResponseEntity<?> retrieveAllCustomers() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setUsername(customerRequest.getUsername());
        customer.setPassword(customerRequest.getPassword());
        customer.setName(customerRequest.getName());
        customerService.save(customer);
        return ResponseEntity.ok("Customer created.");
    }

}
