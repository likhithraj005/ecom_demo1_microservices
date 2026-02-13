package com.likhith.microservices.ordermservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.likhith.microservices.ordermservice.dto.OrderRequest;
import com.likhith.microservices.ordermservice.dto.OrderResponse;
import com.likhith.microservices.ordermservice.dto.UserResponse;
import com.likhith.microservices.ordermservice.model.Order;
import com.likhith.microservices.ordermservice.repository.OrderRepository;

import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;
//	private final RestTemplate restTemplate;
	private final WebClient webClient;


	private static final String USER_SERVICE_URL = "http://localhost:8081/api/users/";

	// CREATE ORDER
	public OrderResponse create(OrderRequest request) {

	    log.info("Creating order for userId: {}", request.getUserId());

	    // Validate user exists
//	    UserResponse user;
//	    try {
//	        user = restTemplate.getForObject(
//	                USER_SERVICE_URL + request.getUserId(),
//	                UserResponse.class
//	        );
//
//	        log.info("User found: {}", user.getName());
//
//	    } catch (HttpClientErrorException.NotFound ex) {
//	        log.error("User not found with id {}", request.getUserId());
//	        throw new RuntimeException("User does not exist");
//	    }
	    
	    UserResponse user;

        try {
            user = webClient.get()
                    .uri("/api/users/{id}", request.getUserId())
                    .retrieve()
                    .bodyToMono(UserResponse.class)
                    .block();  // blocking because we are in traditional MVC

            log.info("User found: {}", user.getName());

        } catch (WebClientResponseException.NotFound ex) {
            log.error("User not found with id {}", request.getUserId());
            throw new RuntimeException("User does not exist");
        }

	    // Save order (no total stored)
	    Order order = Order.builder()
	            .userId(request.getUserId())
	            .productName(request.getProductName())
	            .quantity(request.getQuantity())
	            .price(request.getPrice())
	            .build();

	    Order saved = orderRepository.save(order);

	    log.info("Order saved with id {}", saved.getId());

	    // Calculate total dynamically
	    //double totalAmount = saved.getPrice() * saved.getQuantity();
	    BigDecimal totalAmount = saved.getPrice()
	            .multiply(BigDecimal.valueOf(saved.getQuantity()));


	    log.info("Calculated total amount: {}", totalAmount);

	    //Return response
	    return OrderResponse.builder()
	            .orderId(saved.getId())
	            .productName(saved.getProductName())
	            .quantity(saved.getQuantity())
	            .price(saved.getPrice())
	            .totalAmount(totalAmount)   // only in response
	            .user(user)
	            .build();
	}


	// GET ALL
	public List<Order> getAll() {
		log.info("Fetching all orders");
		return orderRepository.findAll();
	}

	// DELETE
	public void delete(Long id) {
		log.info("Deleting order id {}", id);
		orderRepository.deleteById(id);
	}
	
//	public boolean validateUser(Long userId) {
//
//        String url = "http://localhost:8081/api/users/" + userId;
//
//        try {
//            restTemplate.getForObject(url, Object.class);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
	
	public boolean validateUser(Long userId) {

	    try {
	        webClient.get()
	                .uri("/api/users/{id}", userId)
	                .retrieve()
	                .bodyToMono(Object.class)
	                .block();   // block because we are not fully reactive

	        return true;

	    } catch (Exception e) {
	        log.error("User validation failed for id {}", userId);
	        return false;
	    }
	}
}
