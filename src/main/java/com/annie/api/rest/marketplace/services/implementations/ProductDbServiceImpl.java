package com.annie.api.rest.marketplace.services.implementations;

import com.annie.api.rest.marketplace.models.entities.Category;
import com.annie.api.rest.marketplace.models.entities.Product;
import com.annie.api.rest.marketplace.models.entities.Seller;
import com.annie.api.rest.marketplace.repositories.ProductRepo;
import com.annie.api.rest.marketplace.services.interfaces.ProductDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDbServiceImpl implements ProductDbService {

    private final ProductRepo productRepo;

    @Autowired
    public ProductDbServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public List<Product> findBySeller(Seller seller) {
        return productRepo.findBySeller(seller);
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepo.findByCategory(category);
    }

    @Override
    public void save(Product product) {
        productRepo.save(product);
    }

    @Override
    public Product findById(long id) {
        return productRepo.findById(id).orElse(null);
    }

    @Override
    public void delete(long id) {
        productRepo.deleteById(id);
    }
}
