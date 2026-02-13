package com.kodewala.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodewala.entity.Payment;

@RestController
@RequestMapping("/zepto")
public class PaymentOperationController {

	@PostMapping("/processPayment")
	public ResponseEntity<String>doPayment(@RequestBody Payment payment)
	{
		System.out.println("PaymentOperationController.doPayment()");
		return new ResponseEntity<String>("Payment successfull ,With Details :"+payment,HttpStatus.OK);
	}

	
	
}
