package com.ecommerce.priceapi.infrastructure.adapter.out.persistence.repository;

import com.ecommerce.priceapi.infrastructure.adapter.out.persistence.entity.PriceEntity;
import com.ecommerce.priceapi.infrastructure.adapter.out.persistence.entity.PriceEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaPriceRepository extends JpaRepository<PriceEntity, PriceEntityId> {
    @Query("""
            SELECT price
            FROM PriceEntity price
            WHERE price.brandId = :brandId
              AND price.productId = :productId
              AND :applicationDate BETWEEN price.startDate AND price.endDate
            """)
    List<PriceEntity> findApplicablePrices(
            @Param("brandId") Long brandId,
            @Param("productId") Long productId,
            @Param("applicationDate") LocalDateTime applicationDate
    );
}
