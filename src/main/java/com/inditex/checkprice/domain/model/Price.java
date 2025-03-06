package com.inditex.checkprice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    private Long brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer priceList;
    private Long productId;
    private Integer priority;
    private BigDecimal priceValue;
    private String currency;

    public Price(Long productId, Long brandId, Long priceList, LocalDateTime startDate, LocalDateTime endDate, Double priceValue, String currency) {
        this.productId = productId;
        this.brandId = brandId;
        this.priceList = Math.toIntExact(priceList);
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceValue = BigDecimal.valueOf(priceValue);
        this.currency = currency;

    }
}

