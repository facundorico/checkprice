package com.inditex.checkprice.infrastructure.repository;

import com.inditex.checkprice.domain.model.Price;
import com.inditex.checkprice.domain.repository.PriceRepository;
import com.inditex.checkprice.infrastructure.entity.PriceEntity;
import com.inditex.checkprice.infrastructure.repository.jpa.PriceJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class PriceRepositoryImpl implements PriceRepository {

    private final PriceJpaRepository priceJpaRepository;

    public PriceRepositoryImpl(PriceJpaRepository priceJpaRepository) {
        this.priceJpaRepository = priceJpaRepository;
    }

    @Override
    public Optional<Price> findPriceByDateAndProductIdAndBrandId(LocalDateTime date, Long productId, Long brandId) {
        return priceJpaRepository
                .findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(productId, brandId, date, date)
                .map(this::mapToDomain);
    }

    private Price mapToDomain(PriceEntity priceEntity) {
        return new Price(
                priceEntity.getProductId(),
                priceEntity.getBrandId(),
                priceEntity.getPriceList(),
                priceEntity.getStartDate(),
                priceEntity.getEndDate(),
                priceEntity.getPrice(),
                priceEntity.getCurrency()
        );
    }
}