package com.inditex.checkprice.application.service;

import com.inditex.checkprice.application.dto.PriceResponseDto;
import com.inditex.checkprice.domain.model.Price;
import com.inditex.checkprice.domain.service.PriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceServiceImplTest {

    @Mock
    private PriceService priceService;

    @InjectMocks
    private PriceServiceImpl priceServiceImpl;

    private final Long productId = 35455L;
    private final Long brandId = 1L;
    private final LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);

    private Price mockPrice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockPrice = new Price(
                productId, brandId, 1L,
                LocalDateTime.of(2020, 6, 14, 0, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                35.50,
                "EUR"
        );

        when(priceService.getApplicablePrice(productId, brandId, applicationDate))
                .thenReturn(Optional.of(mockPrice));
    }

    @Test
    @DisplayName("Should return PriceResponseDto when an applicable price exists")
    void testGetApplicablePrice_whenPriceExists() {
        Optional<PriceResponseDto> response = priceServiceImpl.getApplicablePrice(productId, brandId, applicationDate);

        assertTrue(response.isPresent());
        assertEquals(productId, response.get().getProductId());
        assertEquals(brandId, response.get().getBrandId());
        assertEquals(1, response.get().getPriceList());
        assertEquals(LocalDateTime.of(2020, 6, 14, 0, 0, 0), response.get().getStartDate());
        assertEquals(LocalDateTime.of(2020, 12, 31, 23, 59, 59), response.get().getEndDate());
        assertEquals(0, new BigDecimal("35.50").compareTo(response.get().getPrice()));
        assertEquals("EUR", response.get().getCurrency());
    }

    @Test
    @DisplayName("Should return empty when no applicable price exists")
    void testGetApplicablePrice_whenPriceDoesNotExist() {
        when(priceService.getApplicablePrice(productId, brandId, applicationDate))
                .thenReturn(Optional.empty());

        Optional<PriceResponseDto> response = priceServiceImpl.getApplicablePrice(productId, brandId, applicationDate);

        assertFalse(response.isPresent());
    }
}
