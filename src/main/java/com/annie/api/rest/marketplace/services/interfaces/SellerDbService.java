package com.annie.api.rest.marketplace.services.interfaces;

import com.annie.api.rest.marketplace.models.entities.Seller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SellerDbService {

    void save(Seller seller);
    Seller findById(long id);
    List<Seller> findAll();
    void delete(long id);

}
