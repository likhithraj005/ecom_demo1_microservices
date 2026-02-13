package com.likhith.microservices.ordermservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.likhith.microservices.ordermservice.dto.OrderRequest;
import com.likhith.microservices.ordermservice.dto.OrderResponse;
import com.likhith.microservices.ordermservice.model.Order;
import com.likhith.microservices.ordermservice.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	
//		http://localhost:8081/actuator
//		http://localhost:8081/actuator/health
//		http://localhost:8081/actuator/metrics


	@PostMapping
	public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest request) {

		return ResponseEntity.ok(orderService.create(request));
	}

	@GetMapping
	public ResponseEntity<List<Order>> getAll() {
		return ResponseEntity.ok(orderService.getAll());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		orderService.delete(id);
		return ResponseEntity.ok("Order deleted successfully");
	}
	
	@GetMapping("/validate/{userId}")
	public ResponseEntity<String> validateUser(@PathVariable Long userId) {

	    boolean isValid = orderService.validateUser(userId);

	    if (isValid) {
	        return ResponseEntity.ok("User is valid");
	    } else {
	        return ResponseEntity.badRequest().body("User not found");
	    }
	}

}
