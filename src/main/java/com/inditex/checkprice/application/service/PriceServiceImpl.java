package com.inditex.checkprice.application.service;

import com.inditex.checkprice.application.dto.PriceResponseDto;
import com.inditex.checkprice.domain.model.Price;
import com.inditex.checkprice.domain.service.PriceService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PriceServiceImpl {
    private final PriceService priceService;

    public PriceServiceImpl(PriceService priceService) {
        this.priceService = priceService;
    }

    public Optional<PriceResponseDto> getApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate) {
        Optional<Price> price = priceService.getApplicablePrice(productId, brandId, applicationDate);

        return price.map(p -> new PriceResponseDto(
                p.getProductId(),
                p.getBrandId(),
                p.getPriceList(),
                p.getStartDate(),
                p.getEndDate(),
                p.getPriceValue(),
                p.getCurrency()
        ));
    }
}
