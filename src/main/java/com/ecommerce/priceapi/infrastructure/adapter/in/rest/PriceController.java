package com.ecommerce.priceapi.infrastructure.adapter.in.rest;

import com.ecommerce.priceapi.application.dto.PriceQuery;
import com.ecommerce.priceapi.application.dto.PriceResponse;
import com.ecommerce.priceapi.application.port.in.GetApplicablePriceUseCase;
import com.ecommerce.priceapi.infrastructure.adapter.in.rest.request.PriceQueryRequest;
import com.ecommerce.priceapi.infrastructure.adapter.in.rest.response.PriceResponseDto;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/v1/prices")
public class PriceController {
    private final GetApplicablePriceUseCase getApplicablePriceUseCase;

    public PriceController(GetApplicablePriceUseCase getApplicablePriceUseCase) {
        this.getApplicablePriceUseCase = getApplicablePriceUseCase;
    }

    @GetMapping
    public PriceResponseDto getApplicablePrice(@Valid @ModelAttribute PriceQueryRequest request) {
        PriceResponse response = getApplicablePriceUseCase.execute(new PriceQuery(
                request.applicationDate(),
                request.productId(),
                request.brandId()
        ));

        return new PriceResponseDto(
                response.productId(),
                response.brandId(),
                response.priceList(),
                response.startDate(),
                response.endDate(),
                response.price(),
                response.currency()
        );
    }
}
