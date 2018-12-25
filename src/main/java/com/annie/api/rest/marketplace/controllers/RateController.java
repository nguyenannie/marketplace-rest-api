package com.annie.api.rest.marketplace.controllers;

import com.annie.api.rest.marketplace.models.dtos.ApiError;
import com.annie.api.rest.marketplace.models.dtos.RatingDTO;
import com.annie.api.rest.marketplace.models.entities.Rating;
import com.annie.api.rest.marketplace.models.entities.Seller;
import com.annie.api.rest.marketplace.services.implementations.RatingDbServiceImpl;
import com.annie.api.rest.marketplace.services.implementations.SellerDbServiceImpl;
import com.annie.api.rest.marketplace.services.interfaces.RatingDbService;
import com.annie.api.rest.marketplace.services.interfaces.SellerDbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/rating")
@Api(description = "Rating Operation", produces = "application/json", tags = { "Rating" })
public class RateController {

    private final RatingDbService ratingDbService;
    private final SellerDbService sellerDbService;

    @Autowired
    public RateController(RatingDbServiceImpl ratingDbServiceImpl, SellerDbServiceImpl sellerDbServiceImpl) {
        this.ratingDbService = ratingDbServiceImpl;
        this.sellerDbService = sellerDbServiceImpl;
    }

    @ApiOperation(value = "Get Rating of A Seller", tags = { "Rating" })
    @GetMapping(value = "/seller/{id}", produces = "application/json")
    public ResponseEntity<List<Rating>> getRatingsBySeller(@PathVariable long id) {
        Seller seller = sellerDbService.findById(id);
        if (seller == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(seller.getRatings(), HttpStatus.OK);
    }

    @ApiOperation(value = "Rate a Seller", tags = { "Rating" })
    @ApiResponse(code = 201, message = "Created", response = Rating.class)
    @PostMapping(value = "/seller/{id}", produces = "application/json")
    public ResponseEntity<Object> rateSeller(@PathVariable long id, @RequestBody RatingDTO ratingDTO) {
        Seller seller = sellerDbService.findById(id);
        if (seller == null) {
            return ResponseEntity.notFound().build();
        }
        if (ratingDTO.getRate() < 1 || ratingDTO.getRate() > 5) {
            return new ResponseEntity<>(new ApiError("Rating can only be from 1 to 5", LocalDateTime.now()),
                    HttpStatus.BAD_REQUEST);
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
