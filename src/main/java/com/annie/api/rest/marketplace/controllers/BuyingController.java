package com.annie.api.rest.marketplace.controllers;

import com.annie.api.rest.marketplace.models.dtos.ApiError;
import com.annie.api.rest.marketplace.models.entities.Product;
import com.annie.api.rest.marketplace.services.implementations.ProductDbServiceImpl;
import com.annie.api.rest.marketplace.services.interfaces.ProductDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class BuyingController {

    private final ProductDbService productDbService;

    @Autowired
    public BuyingController(ProductDbServiceImpl productDbServiceImpl) {
        this.productDbService = productDbServiceImpl;
    }

    @PostMapping("/buy")
    public ResponseEntity<Object> buyProduct(@RequestParam(value = "productId") long productId,
                                             @RequestParam(value = "quantity", required = false) Integer quantity) {
        Product productToBuy = productDbService.findById(productId);
        if (quantity == null) {
            quantity = 1;
        }
        int currentStock = productToBuy.getStock();
        if (productToBuy.getStock() < quantity) {
            new ResponseEntity<>(new ApiError("Not enough stock. Current stock is " + currentStock,
                    LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }
        productToBuy.setStock(currentStock - quantity);
        productToBuy.setSalesUnit(productToBuy.getSalesUnit() + quantity);
        productDbService.save(productToBuy);
        return new ResponseEntity<>(productToBuy, HttpStatus.OK);
    }
}
