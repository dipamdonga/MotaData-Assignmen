package com.assignment.dipam.service;

import com.assignment.dipam.entity.OrderBO;
import com.assignment.dipam.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private JmsTemplate jmsTemplate;

    @InjectMocks
    private OrderService orderService;

    private OrderBO mockOrder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        // Initialize mockOrder with a valid ID and date
        mockOrder = new OrderBO();
        mockOrder.setId("1"); // Replace "1" with the proper type for ID (String, Long, etc.)
        mockOrder.setOrderDate(new Date());
    }

    @Test
    public void testCreateOrderSuccess() {
        // Arrange
        when(orderRepository.save(any(OrderBO.class))).thenReturn(mockOrder);

        // Act
        OrderBO createdOrder = orderService.createOrder(mockOrder);

        // Assert
        assertNotNull(createdOrder);
        assertEquals(mockOrder.getId(), createdOrder.getId());
        verify(jmsTemplate, times(1)).convertAndSend("order_events", "Order created: " + createdOrder.getId());
    }

    @Test
    public void testUpdateOrderSuccess() {
        // Arrange
        when(orderRepository.save(any(OrderBO.class))).thenReturn(mockOrder);

        // Act
        OrderBO updatedOrder = orderService.updateOrder(mockOrder);

        // Assert
        assertNotNull(updatedOrder);
        assertEquals(mockOrder.getId(), updatedOrder.getId());
        verify(jmsTemplate, times(1)).convertAndSend("order_events", "Order updated: " + updatedOrder.getId());
    }

    @Test
    public void testDeleteOrderSuccess() {
        // Arrange
        doNothing().when(orderRepository).deleteById(mockOrder.getId());

        // Act
        orderService.deleteOrder(mockOrder.getId());

        // Assert
        verify(orderRepository, times(1)).deleteById(mockOrder.getId());
        verify(jmsTemplate, times(1)).convertAndSend("order_events", "Order deleted: " + mockOrder.getId());
    }

    @Test
    public void testFindOrderByIdNotFound() {
        // Arrange
        when(orderRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act
        Optional<OrderBO> result = Optional.ofNullable(orderService.findOrderById("999"));

        // Assert
        assertFalse(result.isPresent());
    }
}
