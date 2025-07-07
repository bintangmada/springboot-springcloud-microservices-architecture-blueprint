package com.bintang.order_service.service;

import com.bintang.order_service.entity.Order;
import com.bintang.order_service.entity.OrderLine;
import com.bintang.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order){
        for(OrderLine orderLine : order.getOrderLines()){
            orderLine.setOrder(order);
        }
        return orderRepository.save(order);
    }

    public Order getOrderById(Long id){
        return orderRepository.findById(id).orElse(null);
    }


}
