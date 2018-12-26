package com.annie.api.rest.marketplace.repositories;

import com.annie.api.rest.marketplace.models.entities.Seller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepo extends CrudRepository<Seller, Long> {

    List<Seller> findAll();
}
