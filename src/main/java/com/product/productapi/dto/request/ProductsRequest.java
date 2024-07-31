package com.product.productapi.dto.request;


import java.math.BigDecimal;

public record ProductsRequest(
        Integer productId,
        String productName,
        String description,
        BigDecimal price,
        Integer quantity
) {
}



