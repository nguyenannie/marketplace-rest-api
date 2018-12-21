package com.annie.api.rest.marketplace.controllers;

import com.annie.api.rest.marketplace.models.dtos.ApiError;
import com.annie.api.rest.marketplace.models.dtos.SellerDTO;
import com.annie.api.rest.marketplace.models.entities.Seller;
import com.annie.api.rest.marketplace.services.implementations.SellerDbServiceImpl;
import com.annie.api.rest.marketplace.services.interfaces.SellerDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class SellerController {

    private final SellerDbService sellerDbService;

    @Autowired
    public SellerController(SellerDbServiceImpl sellerDbServiceImpl) {
        this.sellerDbService = sellerDbServiceImpl;
    }

    @GetMapping("/seller")
    public ResponseEntity<Seller> getSellerById(@RequestParam(value = "id") long id) {
        Seller sellerToGet = sellerDbService.findById(id);
        if (sellerToGet == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(sellerToGet, HttpStatus.OK);
    }

    @GetMapping("/sellers")
    public List<Seller> getAllSellers() {
        return sellerDbService.findAll();
    }

    @PostMapping
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

    @DeleteMapping("/seller")
    public ResponseEntity<Seller> deleteSeller(@RequestParam(value = "id") long id) {
        Seller sellerToDelete = sellerDbService.findById(id);
        if (sellerToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        sellerDbService.delete(id);
        return new ResponseEntity<>(sellerToDelete, HttpStatus.OK);
    }

    @PutMapping("/seller")
    public ResponseEntity<Object> modifySeller(@RequestParam(value = "id") long id,
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
        return new ResponseEntity<>(sellerToModify, HttpStatus.CREATED);
    }
}
