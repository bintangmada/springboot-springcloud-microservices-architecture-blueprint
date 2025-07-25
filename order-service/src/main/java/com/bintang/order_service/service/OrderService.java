package com.bintang.order_service.service;

import com.bintang.order_service.dto.Customer;
import com.bintang.order_service.dto.OrderLineResponse;
import com.bintang.order_service.dto.OrderResponse;
import com.bintang.order_service.dto.Product;
import com.bintang.order_service.entity.Order;
import com.bintang.order_service.entity.OrderLine;
import com.bintang.order_service.repository.OrderRepository;
import com.bintang.order_service.webclient.CustomerClient;
import com.bintang.order_service.webclient.ProductClient;
import com.netflix.discovery.converters.Auto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private ProductClient productClient;

    public Order createOrder(Order order){
        for(OrderLine orderLine : order.getOrderLines()){
            orderLine.setOrder(order);
        }
        return orderRepository.save(order);
    }

    @CircuitBreaker(name = "customerService", fallbackMethod = "fallbackCustomerById")
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
        orderResponse.setCustomer(customerClient.getCustomerById(order.getCustomerId()));
        orderResponse.setOrderLines(new ArrayList<>());

        for(OrderLine orderLine : order.getOrderLines()){
            Product product = productClient.getProductById(orderLine.getProductId());
            OrderLineResponse orderLineResponse = new OrderLineResponse();
            orderLineResponse.setId(orderLine.getId());
            orderLineResponse.setPrice(orderLine.getPrice());
            orderLineResponse.setQuantity(orderLine.getQuantity());
            orderLineResponse.setProduct(product);
            orderResponse.getOrderLines().add(orderLineResponse);
        }

        return orderResponse;
    }

    private OrderResponse fallbackCustomerById(Long id, Throwable throwable){
        return new OrderResponse(); // di return kosong agar tidak terjadi error meskipun ada service yang mati
    }

    public OrderResponse getOrderByOrderNumber(String orderNumber){
        Order order = orderRepository.findByOrderNumber(orderNumber);

        if(order == null){
            return null;
        }

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setOrderNumber(order.getOrderNumber());
        orderResponse.setOrderDate(order.getOrderDate());
        orderResponse.setCustomer(customerClient.getCustomerById(order.getCustomerId()));
        orderResponse.setOrderLines(new ArrayList<>());

        for(OrderLine orderLine : order.getOrderLines()){
            Product product = productClient.getProductById(orderLine.getProductId());
            OrderLineResponse orderLineResponse = new OrderLineResponse();
            orderLineResponse.setId(orderLine.getId());
            orderLineResponse.setPrice(orderLine.getPrice());
            orderLineResponse.setQuantity(orderLine.getQuantity());
            orderLineResponse.setProduct(product);
            orderResponse.getOrderLines().add(orderLineResponse);
        }

        return orderResponse;
    }

//    public Customer getCustomerById(Long id){
//        return restTemplate.getForObject("http://CUSTOMER-SERVICE/api/customer/"+id, Customer.class);
//    }

//    public Product getProductById(Long id){
//        return restTemplate.getForObject("http://PRODUCT-SERVICE/api/product/"+id, Product.class);
//    }

    public List<OrderResponse> getAllOrders(){
        List<Order> allOrder = orderRepository.findAll();
        List<OrderResponse> listOrderResponse = new ArrayList<>();
        for(Order order : allOrder){
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setId(order.getId());
            orderResponse.setOrderDate(order.getOrderDate());
            orderResponse.setOrderNumber(order.getOrderNumber());
            orderResponse.setCustomer(customerClient.getCustomerById(order.getCustomerId()));
            orderResponse.setOrderLines(new ArrayList<>());

            for(OrderLine orderLine : order.getOrderLines()){
                OrderLineResponse orderLineResponse = new OrderLineResponse();
                orderLineResponse.setId(orderLine.getId());
                orderLineResponse.setProduct(productClient.getProductById(orderLine.getProductId()));
                orderLineResponse.setQuantity(orderLine.getQuantity());
                orderLineResponse.setPrice(orderLine.getPrice());
                orderResponse.getOrderLines().add(orderLineResponse);
            }

            listOrderResponse.add(orderResponse);
        }

        return listOrderResponse;
    }
}
