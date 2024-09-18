package com.assignment.dipam.service;

import com.assignment.dipam.entity.CustomerBO;
import com.assignment.dipam.entity.OrderBO;
import com.assignment.dipam.exception.ResourceNotFoundException;
import com.assignment.dipam.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${activemq.order.topic}")
    private String orderTopic;

    public OrderBO createOrder(OrderBO order) {
        order.setCustomer(customerService.findCustomerById(order.getCustomer().getId()));
        OrderBO savedOrder = orderRepository.save(order);
        jmsTemplate.convertAndSend(orderTopic, "OrderBO created: " + savedOrder.getId());
        return savedOrder;
    }

    public OrderBO updateOrder(OrderBO order) {
        order.setCustomer(customerService.findCustomerById(order.getCustomer().getId()));
        OrderBO updatedOrder = orderRepository.findById(order.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with this id :: " + order.getId()));
        jmsTemplate.convertAndSend(orderTopic, "Order updated: " + updatedOrder.getId());
        return updatedOrder;
    }

    public void deleteOrder(String id) {
        orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with this id :: " + id));
        orderRepository.deleteById(id);
        jmsTemplate.convertAndSend(orderTopic, "Order deleted: " + id);
    }

    public OrderBO findOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with this id :: " + id));
    }

    public List<OrderBO> findAllOrder() {
        return orderRepository.findAll();
    }
}
