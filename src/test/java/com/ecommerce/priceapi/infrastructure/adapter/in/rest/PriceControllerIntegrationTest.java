package com.ecommerce.priceapi.infrastructure.adapter.in.rest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvSource({
            "2020-06-14T10:00:00,1,35.50,2020-06-14T00:00:00,2020-12-31T23:59:59",
            "2020-06-14T16:00:00,2,25.45,2020-06-14T15:00:00,2020-06-14T18:30:00",
            "2020-06-14T21:00:00,1,35.50,2020-06-14T00:00:00,2020-12-31T23:59:59",
            "2020-06-15T10:00:00,3,30.50,2020-06-15T00:00:00,2020-06-15T11:00:00",
            "2020-06-16T21:00:00,4,38.95,2020-06-15T16:00:00,2020-12-31T23:59:59"
    })
    void shouldReturnExpectedPriceForEachRequiredScenario(
            String applicationDate,
            int expectedPriceList,
            String expectedPrice,
            String expectedStartDate,
            String expectedEndDate
    ) throws Exception {
        mockMvc.perform(get("/api/v1/prices")
                        .param("applicationDate", applicationDate)
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(expectedPriceList))
                .andExpect(jsonPath("$.startDate").value(expectedStartDate))
                .andExpect(jsonPath("$.endDate").value(expectedEndDate))
                .andExpect(jsonPath("$.price").value(Double.parseDouble(expectedPrice)))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @ParameterizedTest
    @CsvSource({
            "2021-01-01T00:00:00,35455,1",
            "2020-06-14T10:00:00,99999,1"
    })
    void shouldReturnClean404JsonWhenNoApplicablePriceExists(
            String applicationDate,
            long productId,
            long brandId
    ) throws Exception {
        mockMvc.perform(get("/api/v1/prices")
                        .param("applicationDate", applicationDate)
                        .param("productId", String.valueOf(productId))
                        .param("brandId", String.valueOf(brandId)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
