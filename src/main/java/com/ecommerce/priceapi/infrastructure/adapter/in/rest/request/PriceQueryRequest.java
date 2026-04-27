package com.ecommerce.priceapi.infrastructure.adapter.in.rest.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record PriceQueryRequest(
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime applicationDate,
        @NotNull
        Long productId,
        @NotNull
        Long brandId
) {
}
