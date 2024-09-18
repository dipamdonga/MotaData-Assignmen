package com.assignment.dipam.service;

import com.assignment.dipam.entity.CustomerBO;
import com.assignment.dipam.exception.ResourceNotFoundException;
import com.assignment.dipam.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${activemq.customer.topic}")
    private String customerTopic;

    public CustomerBO createCustomer(CustomerBO customer) {
        CustomerBO savedCustomer = customerRepository.save(customer);
        jmsTemplate.convertAndSend(customerTopic, "CustomerBO created: " + savedCustomer.getId());
        return savedCustomer;
    }

    public CustomerBO updateCustomer(CustomerBO customer) {
        CustomerBO updatedCustomer = customerRepository.findById(customer.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with this id :: " + customer.getId()));
        jmsTemplate.convertAndSend(customerTopic, "Customer updated: " + updatedCustomer.getId());
        return updatedCustomer;
    }

    public void deleteCustomer(String id) {
        customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with this id :: " + id));
        customerRepository.deleteById(id);
        jmsTemplate.convertAndSend(customerTopic, "Customer deleted: " + id);
    }

    public CustomerBO findCustomerById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with this id :: " + id));
    }

    public List<CustomerBO> findAllCustomer() {
        return customerRepository.findAll();
    }
}
