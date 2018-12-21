package com.annie.api.rest.marketplace.controllers;

import com.annie.api.rest.marketplace.models.dtos.RatingDTO;
import com.annie.api.rest.marketplace.models.entities.Rating;
import com.annie.api.rest.marketplace.models.entities.Seller;
import com.annie.api.rest.marketplace.services.implementations.RatingDbServiceImpl;
import com.annie.api.rest.marketplace.services.implementations.SellerDbServiceImpl;
import com.annie.api.rest.marketplace.services.interfaces.RatingDbService;
import com.annie.api.rest.marketplace.services.interfaces.SellerDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RateController {

    private final RatingDbService ratingDbService;
    private final SellerDbService sellerDbService;

    @Autowired
    public RateController(RatingDbServiceImpl ratingDbServiceImpl, SellerDbServiceImpl sellerDbServiceImpl) {
        this.ratingDbService = ratingDbServiceImpl;
        this.sellerDbService = sellerDbServiceImpl;
    }

    @GetMapping("/rating")
    public ResponseEntity<List<Rating>> getRatingsBySeller(@RequestParam(value = "sellerId") long sellerId) {
        Seller seller = sellerDbService.findById(sellerId);
        if (seller == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(seller.getRatings(), HttpStatus.OK);
    }

    @PostMapping("/rating")
    public ResponseEntity<Rating> rateSeller(@RequestParam(value = "sellerId") long sellerId,
                                             @RequestBody RatingDTO ratingDTO) {
        Seller seller = sellerDbService.findById(sellerId);
        if (seller == null) {
            return ResponseEntity.notFound().build();
        }
        Rating ratingToSave = Rating.builder()
                .rate(ratingDTO.getRate())
                .feedback(ratingDTO.getFeedback())
                .seller(seller).build();

        ratingDbService.save(ratingToSave);
        sellerDbService.save(seller);
        return new ResponseEntity<>(ratingToSave, HttpStatus.CREATED);
    }
}
