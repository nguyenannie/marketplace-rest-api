package com.annie.api.rest.marketplace.services.interfaces;

import com.annie.api.rest.marketplace.models.entities.Rating;
import com.annie.api.rest.marketplace.models.entities.Seller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingDbService {

    List<Rating> findBySeller(Seller seller);
    Rating findById(long id);
    void save(Rating rating);
}
