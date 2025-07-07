package com.bintang.order_service.service;

import com.bintang.order_service.dto.Customer;
import com.bintang.order_service.dto.OrderLineResponse;
import com.bintang.order_service.dto.OrderResponse;
import com.bintang.order_service.dto.Product;
import com.bintang.order_service.entity.Order;
import com.bintang.order_service.entity.OrderLine;
import com.bintang.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Order createOrder(Order order){
        for(OrderLine orderLine : order.getOrderLines()){
            orderLine.setOrder(order);
        }
        return orderRepository.save(order);
    }

    public OrderResponse getOrderById(Long id){
        Optional<Order> optOrder = orderRepository.findById(id);

        if(!optOrder.isPresent()){
            return null;
        }
        Order order = optOrder.get();

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setOrderNumber(order.getOrderNumber());
        orderResponse.setOrderDate(order.getOrderDate());
        orderResponse.setCustomer(getCustomerById(order.getCustomerId()));
        orderResponse.setListOrderLines(new ArrayList<>());

        for(OrderLine orderLine : order.getOrderLines()){
            Product product = getProductById(orderLine.getProductId());
            OrderLineResponse orderLineResponse = new OrderLineResponse();
            orderLineResponse.setId(orderLine.getId());
            orderLineResponse.setPrice(orderLine.getPrice());
            orderLineResponse.setQuantity(orderLine.getQuantity());
            orderLineResponse.setProduct(product);
            orderResponse.getListOrderLines().add(orderLineResponse);
        }

        return orderResponse;
    }

    public Customer getCustomerById(Long id){
        return restTemplate.getForObject("http://localhost:8081/api/customer/"+id, Customer.class);
    }

    public Product getProductById(Long id){
        return restTemplate.getForObject("http://localhost:8082/api/product/"+id, Product.class);
    }
}
