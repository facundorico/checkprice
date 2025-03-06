package com.inditex.checkprice.api.controller;

import com.inditex.checkprice.application.dto.PriceResponseDto;
import com.inditex.checkprice.application.service.PriceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceController.class)
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceServiceImpl priceService;

    private final Long productId = 35455L;
    private final Long brandId = 1L;

    @BeforeEach
    void setUp() {
        when(priceService.getApplicablePrice(productId, brandId, LocalDateTime.of(2020, 6, 14, 10, 0, 0)))
                .thenReturn(Optional.of(new PriceResponseDto(
                        productId, brandId, 1,
                        LocalDateTime.of(2020, 6, 14, 0, 0, 0),
                        LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                        new BigDecimal("35.50"), "EUR")));

        when(priceService.getApplicablePrice(productId, brandId, LocalDateTime.of(2020, 6, 14, 16, 0, 0)))
                .thenReturn(Optional.of(new PriceResponseDto(
                        productId, brandId, 2,
                        LocalDateTime.of(2020, 6, 14, 15, 0, 0),
                        LocalDateTime.of(2020, 6, 14, 18, 30, 0),
                        new BigDecimal("25.45"), "EUR")));

        when(priceService.getApplicablePrice(productId, brandId, LocalDateTime.of(2020, 6, 14, 21, 0, 0)))
                .thenReturn(Optional.of(new PriceResponseDto(
                        productId, brandId, 1,
                        LocalDateTime.of(2020, 6, 14, 0, 0, 0),
                        LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                        new BigDecimal("35.50"), "EUR")));

        when(priceService.getApplicablePrice(productId, brandId, LocalDateTime.of(2020, 6, 15, 10, 0, 0)))
                .thenReturn(Optional.of(new PriceResponseDto(
                        productId, brandId, 3,
                        LocalDateTime.of(2020, 6, 15, 0, 0, 0),
                        LocalDateTime.of(2020, 6, 15, 23, 59, 59),
                        new BigDecimal("30.50"), "EUR")));

        when(priceService.getApplicablePrice(productId, brandId, LocalDateTime.of(2020, 6, 16, 21, 0, 0)))
                .thenReturn(Optional.of(new PriceResponseDto(
                        productId, brandId, 4,
                        LocalDateTime.of(2020, 6, 16, 16, 0, 0),
                        LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                        new BigDecimal("38.95"), "EUR")));
    }

    @Test
    @DisplayName("testGetApplicablePrice_at10AM_on14thJune")
    void testGetApplicablePrice_at10AM_on14thJune() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("date", "2020-06-14 10:00:00")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.priceList").value(1));
    }

    @Test
    @DisplayName("testGetApplicablePrice_at4PM_on14thJune")
    void testGetApplicablePrice_at4PM_on14thJune() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("date", "2020-06-14 16:00:00")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(25.45))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.priceList").value(2));
    }

    @Test
    @DisplayName("testGetApplicablePrice_at9PM_on14thJune")
    void testGetApplicablePrice_at9PM_on14thJune() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("date", "2020-06-14 21:00:00")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.priceList").value(1));
    }

    @Test
    @DisplayName("testGetApplicablePrice_at10AM_on15thJune")
    void testGetApplicablePrice_at10AM_on15thJune() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("date", "2020-06-15 10:00:00")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(30.50))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.priceList").value(3));
    }

    @Test
    @DisplayName("testGetApplicablePrice_at9PM_on16thJune")
    void testGetApplicablePrice_at9PM_on16thJune() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("date", "2020-06-16 21:00:00")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(38.95))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.priceList").value(4));
    }
}


