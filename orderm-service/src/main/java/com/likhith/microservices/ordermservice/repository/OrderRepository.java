package com.likhith.microservices.ordermservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.likhith.microservices.ordermservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
