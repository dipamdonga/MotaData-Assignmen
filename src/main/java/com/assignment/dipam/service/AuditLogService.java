package com.assignment.dipam.service;

import com.assignment.dipam.entity.AuditLogBO;
import com.assignment.dipam.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @JmsListener(destination = "customer_events")
    public void listenToCustomerEvents(String message) {
        AuditLogBO auditLog = new AuditLogBO();
        auditLog.setMessage(message);
        auditLog.setTableName("customer");
        auditLogRepository.save(auditLog);
    }

    @JmsListener(destination = "order_events")
    public void listenToOrderEvents(String message) {
        AuditLogBO auditLog = new AuditLogBO();
        auditLog.setMessage(message);
        auditLog.setTableName("order");
        auditLogRepository.save(auditLog);
    }
}
