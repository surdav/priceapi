package com.ecommerce.priceapi.application.dto;

import java.time.LocalDateTime;

public record PriceQuery(
        LocalDateTime applicationDate,
        Long productId,
        Long brandId
) {
}