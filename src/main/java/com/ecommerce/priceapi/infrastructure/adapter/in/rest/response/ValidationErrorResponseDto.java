package com.ecommerce.priceapi.infrastructure.adapter.in.rest.response;

import java.time.Instant;
import java.util.List;

public record ValidationErrorResponseDto(
        int status,
        String error,
        String message,
        Instant timestamp,
        List<String> details
) {
}
