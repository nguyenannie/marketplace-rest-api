package com.annie.api.rest.marketplace.services.interfaces;

import com.annie.api.rest.marketplace.models.entities.Category;
import com.annie.api.rest.marketplace.models.entities.Product;
import com.annie.api.rest.marketplace.models.entities.Seller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductDbService {

    List<Product> findAll();
    void save(Product product);
    Product findById(long id);
    void delete(long id);

}
