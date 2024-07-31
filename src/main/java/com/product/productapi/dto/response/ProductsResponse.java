package com.product.productapi.dto.response;

import java.math.BigDecimal;

public record ProductsResponse(
        Integer productId,
        String productName,
        String description,
        BigDecimal price,
        Integer quantity
) {
}

