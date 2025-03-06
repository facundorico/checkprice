package com.inditex.checkprice.api.controller;

import com.inditex.checkprice.application.dto.PriceResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PriceControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/prices";
    }

    @Test
    @DisplayName("Should return the correct price for June 14th at 10:00")
    void testGetApplicablePrice_case1() {
        String url = getBaseUrl() + "?date=2020-06-14 10:00:00&productId=35455&brandId=1";

        ResponseEntity<PriceResponseDto> response = restTemplate.exchange(url, HttpMethod.GET, null, PriceResponseDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(35455L, response.getBody().getProductId());
        assertEquals(1L, response.getBody().getBrandId());
    }

    @Test
    @DisplayName("Should return 404 for an invalid date")
    void testGetApplicablePrice_notFound() {
        String url = getBaseUrl() + "?date=2025-06-14 10:00:00&productId=35455&brandId=1";

        ResponseEntity<PriceResponseDto> response = restTemplate.exchange(url, HttpMethod.GET, null, PriceResponseDto.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

