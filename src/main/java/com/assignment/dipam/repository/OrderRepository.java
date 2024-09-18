package com.assignment.dipam.repository;

import com.assignment.dipam.entity.OrderBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderBO, String> {

}
