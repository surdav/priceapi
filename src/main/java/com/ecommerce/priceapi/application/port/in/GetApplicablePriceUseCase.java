package com.ecommerce.priceapi.application.port.in;

import com.ecommerce.priceapi.application.dto.PriceQuery;
import com.ecommerce.priceapi.application.dto.PriceResponse;

public interface GetApplicablePriceUseCase {
    PriceResponse execute(PriceQuery query);
}