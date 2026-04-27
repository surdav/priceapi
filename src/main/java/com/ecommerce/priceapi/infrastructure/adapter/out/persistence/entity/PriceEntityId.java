package com.ecommerce.priceapi.infrastructure.adapter.out.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class PriceEntityId implements Serializable {
    private Long brandId;
    private Long productId;
    private Integer priceList;
    private LocalDateTime startDate;

    public PriceEntityId() {
    }

    public PriceEntityId(Long brandId, Long productId, Integer priceList, LocalDateTime startDate) {
        this.brandId = brandId;
        this.productId = productId;
        this.priceList = priceList;
        this.startDate = startDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PriceEntityId that)) {
            return false;
        }
        return Objects.equals(brandId, that.brandId)
                && Objects.equals(productId, that.productId)
                && Objects.equals(priceList, that.priceList)
                && Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandId, productId, priceList, startDate);
    }
}
