package com.inditex.checkprice.domain.service;

import com.inditex.checkprice.domain.model.Price;
import com.inditex.checkprice.domain.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

@Service
public class PriceService {
    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Optional<Price> getApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate) {
        Optional<Price> prices = priceRepository.findPriceByDateAndProductIdAndBrandId(applicationDate, productId, brandId);

        if (prices.isEmpty()) {
            return Optional.empty();
        }

        return prices.stream()
                .max(Comparator.comparingInt(Price::getPriority));
    }
}
