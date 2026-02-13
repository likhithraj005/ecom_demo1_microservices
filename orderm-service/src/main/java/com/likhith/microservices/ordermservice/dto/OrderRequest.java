package com.likhith.microservices.ordermservice.dto;


import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderRequest {

    private Long userId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
}
