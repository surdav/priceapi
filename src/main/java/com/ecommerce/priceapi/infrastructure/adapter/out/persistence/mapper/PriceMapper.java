package com.ecommerce.priceapi.infrastructure.adapter.out.persistence.mapper;

import com.ecommerce.priceapi.domain.model.Price;
import com.ecommerce.priceapi.infrastructure.adapter.out.persistence.entity.PriceEntity;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {
    public Price toDomain(PriceEntity entity) {
        return new Price(
                entity.getBrandId(),
                entity.getProductId(),
                entity.getPriceList(),
                entity.getPriority(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPrice(),
                entity.getCurrency()
        );
    }
}
