package com.bintang.order_service.controller;

import com.bintang.order_service.dto.OrderResponse;
import com.bintang.order_service.dto.Product;
import com.bintang.order_service.entity.Order;
import com.bintang.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable("id") Long id){
        return orderService.getOrderById(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order){
        return orderService.createOrder(order);
    }

    @GetMapping()
    public List<OrderResponse> getAllOrders(){
        return orderService.getAllOrders();
    }
}
