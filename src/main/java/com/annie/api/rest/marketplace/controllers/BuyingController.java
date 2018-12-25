package com.annie.api.rest.marketplace.controllers;

import com.annie.api.rest.marketplace.models.dtos.ApiError;
import com.annie.api.rest.marketplace.models.entities.Product;
import com.annie.api.rest.marketplace.services.implementations.ProductDbServiceImpl;
import com.annie.api.rest.marketplace.services.interfaces.ProductDbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Api(description = "Buy Operation", produces = "application/json", tags = { "Buy" })
public class BuyingController {

    private final ProductDbService productDbService;

    @Autowired
    public BuyingController(ProductDbServiceImpl productDbServiceImpl) {
        this.productDbService = productDbServiceImpl;
    }

    @ApiOperation(value = "Buy a product", tags = { "Buy" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Buy operation succeeded", response = Product.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiError.class),
            @ApiResponse(code = 404, message = "Product not found")
    })
    @PostMapping(value = "/buy", produces = "application/json")
    public ResponseEntity<Object> buyProduct(@RequestParam(value = "productId") long productId,
                                             @RequestParam(value = "quantity", required = false) Integer quantity) {
        Product productToBuy = productDbService.findById(productId);
        if (productToBuy == null) {
            return ResponseEntity.notFound().build();
        }
        if (quantity == null) {
            quantity = 1;
        }
        int currentStock = productToBuy.getStock();
        if (productToBuy.getStock() < quantity) {
            return new ResponseEntity<>(new ApiError("Not enough stock. Current stock is " + currentStock,
                    LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }
        productToBuy.setStock(currentStock - quantity);
        productToBuy.setSalesUnit(productToBuy.getSalesUnit() + quantity);
        productDbService.save(productToBuy);
        return new ResponseEntity<>(productToBuy, HttpStatus.OK);
    }
}
