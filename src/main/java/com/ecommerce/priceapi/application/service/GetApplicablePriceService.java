package com.ecommerce.priceapi.application.service;

import com.ecommerce.priceapi.application.dto.PriceQuery;
import com.ecommerce.priceapi.application.dto.PriceResponse;
import com.ecommerce.priceapi.application.port.in.GetApplicablePriceUseCase;
import com.ecommerce.priceapi.application.port.out.PriceRepositoryPort;
import com.ecommerce.priceapi.domain.exception.PriceNotFoundException;
import com.ecommerce.priceapi.domain.model.Price;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class GetApplicablePriceService implements GetApplicablePriceUseCase {
    private final PriceRepositoryPort priceRepositoryPort;

    public GetApplicablePriceService(PriceRepositoryPort priceRepositoryPort) {
        this.priceRepositoryPort = priceRepositoryPort;
    }

    @Override
    public PriceResponse execute(PriceQuery query) {
        Price selectedPrice = priceRepositoryPort.findApplicablePrices(
                        query.brandId(),
                        query.productId(),
                        query.applicationDate()
                ).stream()
                .max(Comparator.comparing(Price::priority))
                .orElseThrow(() -> new PriceNotFoundException(
                        "No applicable price found for brandId=%d, productId=%d, applicationDate=%s"
                                .formatted(query.brandId(), query.productId(), query.applicationDate())
                ));
        return new PriceResponse(
                selectedPrice.productId(),
                selectedPrice.brandId(),
                selectedPrice.priceList(),
                selectedPrice.startDate(),
                selectedPrice.endDate(),
                selectedPrice.price(),
                selectedPrice.currency()
        );
    }
}