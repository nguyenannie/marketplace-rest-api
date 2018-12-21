package com.annie.api.rest.marketplace.repositories;

import com.annie.api.rest.marketplace.models.entities.Rating;
import com.annie.api.rest.marketplace.models.entities.Seller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepo extends CrudRepository<Rating, Long> {

    List<Rating> findBySeller(Seller seller);
}
