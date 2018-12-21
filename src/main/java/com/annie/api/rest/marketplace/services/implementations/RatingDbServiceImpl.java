package com.annie.api.rest.marketplace.services.implementations;

import com.annie.api.rest.marketplace.models.entities.Rating;
import com.annie.api.rest.marketplace.models.entities.Seller;
import com.annie.api.rest.marketplace.repositories.RatingRepo;
import com.annie.api.rest.marketplace.services.interfaces.RatingDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingDbServiceImpl implements RatingDbService {

    private final RatingRepo ratingRepo;

    @Autowired
    public RatingDbServiceImpl(RatingRepo ratingRepo) {
        this.ratingRepo = ratingRepo;
    }

    @Override
    public List<Rating> findBySeller(Seller seller) {
        return ratingRepo.findBySeller(seller);
    }

    @Override
    public Rating findById(long id) {
        return ratingRepo.findById(id).orElse(null);
    }

    @Override
    public void save(Rating rating) {
        ratingRepo.save(rating);
    }
}
