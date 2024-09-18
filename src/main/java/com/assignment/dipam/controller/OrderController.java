package com.assignment.dipam.controller;

import com.assignment.dipam.entity.OrderBO;
import com.assignment.dipam.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderBO> createOrder(@RequestBody OrderBO order) {
        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderBO>> getAllOrders() {
        return new ResponseEntity<>(orderService.findAllOrder(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderBO> getOrderById(@PathVariable("id") String id) {
        return new ResponseEntity<>(orderService.findOrderById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<OrderBO> updateOrder(@RequestBody OrderBO orderDetails) {
        return new ResponseEntity<>(orderService.updateOrder(orderDetails), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable("id") String id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
