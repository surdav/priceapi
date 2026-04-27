package com.ecommerce.priceapi.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Price(
        Long brandId,
        Long productId,
        Integer priceList,
        Integer priority,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price,
        String currency
) {
    public boolean appliesTo(LocalDateTime applicationDate) {
        return (applicationDate.isEqual(startDate) || applicationDate.isAfter(startDate))
                && (applicationDate.isEqual(endDate) || applicationDate.isBefore(endDate));
    }
}