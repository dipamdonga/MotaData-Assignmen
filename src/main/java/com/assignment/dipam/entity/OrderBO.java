package com.assignment.dipam.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "orders")
public class OrderBO extends BaseBO {

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerBO customer;

    @Temporal(TemporalType.DATE)
    private Date orderDate;
}
