package com.inditex.checkprice.api.controller;

import com.inditex.checkprice.application.dto.PriceResponseDto;
import com.inditex.checkprice.application.service.PriceServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final PriceServiceImpl priceService;

    public PriceController(PriceServiceImpl priceService) {
        this.priceService = priceService;
    }

    /**
     * Endpoint para obtener el precio aplicable a un producto en una fecha específica.
     *
     * @param date Fecha en formato "yyyy-MM-dd HH:mm:ss"
     * @param productId Identificador del producto.
     * @param brandId Identificador de la marca.
     * @return ResponseEntity con los datos del precio aplicable o código 404 si no se encuentra.
     */
    @GetMapping
    public ResponseEntity<PriceResponseDto> getApplicablePrice(
            @RequestParam("date") String date,
            @RequestParam("productId") Long productId,
            @RequestParam("brandId") Long brandId) {

        LocalDateTime applicationDate = LocalDateTime.parse(date.replace(" ", "T"));

        Optional<PriceResponseDto> priceResponse = priceService.getApplicablePrice(
                productId,
                brandId,
                applicationDate
        );

        return priceResponse.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
