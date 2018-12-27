package com.annie.api.rest.marketplace.controllers;

import com.annie.api.rest.marketplace.models.dtos.ApiError;
import com.annie.api.rest.marketplace.models.dtos.SellerDTO;
import com.annie.api.rest.marketplace.models.entities.Seller;
import com.annie.api.rest.marketplace.services.implementations.SellerDbServiceImpl;
import com.annie.api.rest.marketplace.services.interfaces.SellerDbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Api(description = "Seller Operations", produces = "application/json", tags = { "Seller" })
public class SellerController {

    private final SellerDbService sellerDbService;

    @Autowired
    public SellerController(SellerDbServiceImpl sellerDbServiceImpl) {
        this.sellerDbService = sellerDbServiceImpl;
    }

    @ApiOperation(value = "Get Seller", tags = { "Seller" })
    @GetMapping(value = "/seller/{id}", produces = "application/json")
    public ResponseEntity<Seller> getSellerById(@PathVariable(value = "id") long id) {
        Seller sellerToGet = sellerDbService.findById(id);
        if (sellerToGet == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(sellerToGet, HttpStatus.OK);
    }

    @ApiOperation(value = "Get All Sellers", tags = { "Seller" })
    @GetMapping(value = "/sellers", produces = "application/json")
    public List<Seller> getAllSellers() {
        return sellerDbService.findAll();
    }

    @ApiOperation(value = "Create Seller", tags = { "Seller" })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Seller.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiError.class)
    })
    @PostMapping(value = "/seller", produces = "application/json")
    public ResponseEntity<Object> saveSeller(@RequestBody SellerDTO sellerDTO) {
        if (!sellerDTO.isValid()) {
            return new ResponseEntity<>(new ApiError("Invalid request body, please check the documentation!",
                    LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }
        Seller sellerToBeSaved = Seller.builder()
                .firstName(sellerDTO.getFirstName())
                .lastName(sellerDTO.getLastName())
                .email(sellerDTO.getEmail())
                .address(sellerDTO.getAddress())
                .phoneNumber(sellerDTO.getPhoneNumber()).build();
        sellerDbService.save(sellerToBeSaved);
        return new ResponseEntity<>(sellerToBeSaved, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete Seller", tags = { "Seller" })
    @ApiResponse(code = 404, message = "Resource Not Found")
    @DeleteMapping(value = "/seller/{id}", produces = "application/json")
    public ResponseEntity<Seller> deleteSeller(@PathVariable long id) {
        Seller sellerToDelete = sellerDbService.findById(id);
        if (sellerToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        sellerDbService.delete(id);
        return new ResponseEntity<>(sellerToDelete, HttpStatus.OK);
    }

    @ApiOperation(value = "Modify Seller", tags = { "Seller" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Modified Successfully", response = Seller.class),
            @ApiResponse(code = 404, message = "Resource Not Found")
    })
    @PutMapping(value = "/seller/{id}", produces = "application/json")
    public ResponseEntity<Object> modifySeller(@PathVariable long id,
                                               @RequestBody SellerDTO sellerDTO) {
        Seller sellerToModify = sellerDbService.findById(id);
        if (sellerToModify == null) {
            return ResponseEntity.notFound().build();
        }
        if (!sellerDTO.isValid()) {
            return new ResponseEntity<>(new ApiError("Invalid request body, please check the documentation!",
                    LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }
        sellerToModify.setFirstName(sellerDTO.getFirstName());
        sellerToModify.setLastName(sellerDTO.getLastName());
        sellerToModify.setAddress(sellerDTO.getAddress());
        sellerToModify.setEmail(sellerDTO.getEmail());
        sellerToModify.setPhoneNumber(sellerDTO.getPhoneNumber());
        sellerDbService.save(sellerToModify);
        return new ResponseEntity<>(sellerToModify, HttpStatus.OK);
    }
}
