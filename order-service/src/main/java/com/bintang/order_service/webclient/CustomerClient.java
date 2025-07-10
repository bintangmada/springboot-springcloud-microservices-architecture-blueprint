package com.bintang.order_service.webclient;

import com.bintang.order_service.dto.Customer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface CustomerClient {

    @GetExchange("/api/customer/{id}")
    public Customer getCustomerById(@PathVariable("id") Long id);
}
