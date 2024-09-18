package com.assignment.dipam.service;

import com.assignment.dipam.entity.CustomerBO;
import com.assignment.dipam.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private JmsTemplate jmsTemplate;

    @InjectMocks
    private CustomerService customerService;

    private CustomerBO mockCustomer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        // Initialize mockCustomer with a valid ID and name
        mockCustomer = new CustomerBO();
        mockCustomer.setId("1");  // Replace "1" with the correct type for your ID
        mockCustomer.setName("John Doe");
    }

    @Test
    public void testCreateCustomerSuccess() {
        // Arrange
        when(customerRepository.save(any(CustomerBO.class))).thenReturn(mockCustomer);

        // Act
        CustomerBO createdCustomer = customerService.createCustomer(mockCustomer);

        // Assert
        assertNotNull(createdCustomer);
        assertEquals(mockCustomer.getId(), createdCustomer.getId());
        verify(jmsTemplate, times(1)).convertAndSend("customer_events", "Customer created: " + createdCustomer.getId());
    }

    @Test
    public void testUpdateCustomerSuccess() {
        // Arrange
        when(customerRepository.save(any(CustomerBO.class))).thenReturn(mockCustomer);

        // Act
        CustomerBO updatedCustomer = customerService.updateCustomer(mockCustomer);

        // Assert
        assertNotNull(updatedCustomer);
        assertEquals(mockCustomer.getId(), updatedCustomer.getId());
        verify(jmsTemplate, times(1)).convertAndSend("customer_events", "Customer updated: " + updatedCustomer.getId());
    }

    @Test
    public void testDeleteCustomerSuccess() {
        // Arrange
        doNothing().when(customerRepository).deleteById(mockCustomer.getId());

        // Act
        customerService.deleteCustomer(mockCustomer.getId());

        // Assert
        verify(customerRepository, times(1)).deleteById(mockCustomer.getId());
        verify(jmsTemplate, times(1)).convertAndSend("customer_events", "Customer deleted: " + mockCustomer.getId());
    }

    @Test
    public void testFindCustomerByIdNotFound() {
        // Arrange
        when(customerRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act
        Optional<CustomerBO> result = Optional.ofNullable(customerService.findCustomerById("999"));

        // Assert
        assertFalse(result.isPresent());
    }
}
