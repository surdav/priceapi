package com.ecommerce.priceapi.application.service;

import com.ecommerce.priceapi.application.dto.PriceQuery;
import com.ecommerce.priceapi.application.dto.PriceResponse;
import com.ecommerce.priceapi.application.port.out.PriceRepositoryPort;
import com.ecommerce.priceapi.domain.exception.PriceNotFoundException;
import com.ecommerce.priceapi.domain.model.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetApplicablePriceServiceTest {
    @Mock
    private PriceRepositoryPort priceRepositoryPort;

    @InjectMocks
    private GetApplicablePriceService service;

    @Test
    void shouldReturnTheOnlyApplicablePrice() {
        PriceQuery query = new PriceQuery(LocalDateTime.parse("2020-06-14T10:00:00"), 35455L, 1L);
        Price price = price(
                1L,
                35455L,
                1,
                0,
                "2020-06-14T00:00:00",
                "2020-12-31T23:59:59",
                "35.50"
        );

        when(priceRepositoryPort.findApplicablePrices(1L, 35455L, query.applicationDate()))
                .thenReturn(List.of(price));

        PriceResponse response = service.execute(query);

        assertThat(response.priceList()).isEqualTo(1);
        assertThat(response.price()).isEqualByComparingTo("35.50");
        assertThat(response.currency()).isEqualTo("EUR");
        verify(priceRepositoryPort).findApplicablePrices(1L, 35455L, query.applicationDate());
    }

    @Test
    void shouldSelectTheHighestPriorityPriceUsingStreamMax() {
        PriceQuery query = new PriceQuery(LocalDateTime.parse("2020-06-14T16:00:00"), 35455L, 1L);
        Price basePrice = price(
                1L,
                35455L,
                1,
                0,
                "2020-06-14T00:00:00",
                "2020-12-31T23:59:59",
                "35.50"
        );
        Price promotionalPrice = price(
                1L,
                35455L,
                2,
                1,
                "2020-06-14T15:00:00",
                "2020-06-14T18:30:00",
                "25.45"
        );

        when(priceRepositoryPort.findApplicablePrices(1L, 35455L, query.applicationDate()))
                .thenReturn(List.of(basePrice, promotionalPrice));

        PriceResponse response = service.execute(query);

        assertThat(response.priceList()).isEqualTo(2);
        assertThat(response.price()).isEqualByComparingTo("25.45");
        assertThat(response.startDate()).isEqualTo(LocalDateTime.parse("2020-06-14T15:00:00"));
        assertThat(response.endDate()).isEqualTo(LocalDateTime.parse("2020-06-14T18:30:00"));
    }

    @Test
    void shouldThrowWhenNoApplicablePriceExists() {
        PriceQuery query = new PriceQuery(LocalDateTime.parse("2021-01-01T00:00:00"), 35455L, 1L);

        when(priceRepositoryPort.findApplicablePrices(1L, 35455L, query.applicationDate()))
                .thenReturn(List.of());

        assertThatThrownBy(() -> service.execute(query))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessageContaining("No applicable price found");
    }

    private static Price price(
            Long brandId,
            Long productId,
            Integer priceList,
            Integer priority,
            String startDate,
            String endDate,
            String amount
    ) {
        return new Price(
                brandId,
                productId,
                priceList,
                priority,
                LocalDateTime.parse(startDate),
                LocalDateTime.parse(endDate),
                new BigDecimal(amount),
                "EUR"
        );
    }
}
