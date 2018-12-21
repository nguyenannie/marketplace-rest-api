package com.annie.api.rest.marketplace.services.implementations;

import com.annie.api.rest.marketplace.models.entities.Seller;
import com.annie.api.rest.marketplace.repositories.SellerRepo;
import com.annie.api.rest.marketplace.services.interfaces.SellerDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerDbServiceImpl implements SellerDbService {

    private final SellerRepo sellerRepo;

    @Autowired
    public SellerDbServiceImpl(SellerRepo sellerRepo) {
        this.sellerRepo = sellerRepo;
    }

    @Override
    public void save(Seller seller) {
        sellerRepo.save(seller);
    }

    @Override
    public Seller findById(long id) {
        return sellerRepo.findById(id).orElse(null);
    }

    @Override
    public List<Seller> findAll() {
        return sellerRepo.findAll();
    }

    @Override
    public void delete(long id) {
        sellerRepo.deleteById(id);
    }
}
