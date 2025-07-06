package com.bintang.customerservice.service;

import com.bintang.customerservice.entity.Customer;
import com.bintang.customerservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        Customer cekEmail = customerRepository.findByEmail(customer.getEmail());
        if (cekEmail != null) {
            throw new RuntimeException("Email already exists");
        }
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElse(null);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
