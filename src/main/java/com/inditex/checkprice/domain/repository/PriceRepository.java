package com.inditex.checkprice.domain.repository;

import com.inditex.checkprice.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository {

    Optional<Price> findPriceByDateAndProductIdAndBrandId(LocalDateTime date, Long productId, Long brandId);
}