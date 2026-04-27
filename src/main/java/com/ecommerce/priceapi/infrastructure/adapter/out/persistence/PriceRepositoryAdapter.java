package com.ecommerce.priceapi.infrastructure.adapter.out.persistence;

import com.ecommerce.priceapi.application.port.out.PriceRepositoryPort;
import com.ecommerce.priceapi.domain.model.Price;
import com.ecommerce.priceapi.infrastructure.adapter.out.persistence.mapper.PriceMapper;
import com.ecommerce.priceapi.infrastructure.adapter.out.persistence.repository.JpaPriceRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PriceRepositoryAdapter implements PriceRepositoryPort {
    private final JpaPriceRepository jpaPriceRepository;
    private final PriceMapper priceMapper;

    public PriceRepositoryAdapter(JpaPriceRepository jpaPriceRepository, PriceMapper priceMapper) {
        this.jpaPriceRepository = jpaPriceRepository;
        this.priceMapper = priceMapper;
    }

    @Override
    public List<Price> findApplicablePrices(Long brandId, Long productId, LocalDateTime applicationDate) {
        return jpaPriceRepository.findApplicablePrices(brandId, productId, applicationDate)
                .stream()
                .map(priceMapper::toDomain)
                .toList();
    }
}
