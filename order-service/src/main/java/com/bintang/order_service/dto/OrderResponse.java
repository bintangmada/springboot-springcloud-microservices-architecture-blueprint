package com.bintang.order_service.dto;

import java.util.Date;
import java.util.List;

public class OrderResponse {

    private Long id;
    private String orderNumber;
    private Date orderDate;
    private Customer customer;
    private List<OrderLineResponse> orderLines;

    public OrderResponse(Long id, String orderNumber, Date orderDate, Customer customer, List<OrderLineResponse> orderLines) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.customer = customer;
        this.orderLines = orderLines;
    }

    public OrderResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderLineResponse> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineResponse> orderLines) {
        this.orderLines = orderLines;
    }
}
