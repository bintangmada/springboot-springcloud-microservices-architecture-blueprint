package com.bintang.order_service.webclient;

import com.bintang.order_service.dto.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ProductClient {

    @GetExchange("/api/product/{id}")
    public Product getProductById(@PathVariable("id") Long id);
}
