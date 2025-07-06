package com.bintang.customerservice.controller;

import com.bintang.customerservice.dto.SearchEmailRequest;
import com.bintang.customerservice.entity.Customer;
import com.bintang.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") Long  id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/find-by-email")
    public Customer getCustomerByEmail(@RequestBody SearchEmailRequest searchEmailRequest) {
        return customerService.findCustomerByEmail(searchEmailRequest.getEmail());
    }
}
