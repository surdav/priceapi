package com.ecommerce.priceapi.application.port.out;

import com.ecommerce.priceapi.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepositoryPort {
    List<Price> findApplicablePrices(Long brandId, Long productId, LocalDateTime applicationDate);
}