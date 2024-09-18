package com.assignment.dipam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "customer")
public class CustomerBO extends BaseBO{

    private String name;
    private String surname;
    private String email;
}
