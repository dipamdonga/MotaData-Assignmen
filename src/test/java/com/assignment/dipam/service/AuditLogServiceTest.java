package com.assignment.dipam.service;

import com.assignment.dipam.repository.AuditLogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class AuditLogServiceTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Test
    public void testOrderCreatedAuditLog() throws InterruptedException {
        jmsTemplate.convertAndSend("order_events", "Order created: 1");

        // Allow some time for the message to be consumed
        Thread.sleep(1000);

        assertFalse(auditLogRepository.findAll().isEmpty());
    }

    @Test
    public void testCustomerCreatedAuditLog() throws InterruptedException {
        jmsTemplate.convertAndSend("customer_events", "Customer created: 1");

        // Allow some time for the message to be consumed
        Thread.sleep(1000);

        assertFalse(auditLogRepository.findAll().isEmpty());
    }
}
