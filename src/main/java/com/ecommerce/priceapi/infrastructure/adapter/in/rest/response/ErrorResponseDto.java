package com.ecommerce.priceapi.infrastructure.adapter.in.rest.response;

import java.time.Instant;

public record ErrorResponseDto(
        int status,
        String error,
        String message,
        Instant timestamp
) {
}
