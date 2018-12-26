package com.annie.api.rest.marketplace.services.interfaces;

import com.annie.api.rest.marketplace.models.entities.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryDbService {

    List<Category> findAll();
    Category findByName(String name);
    void save(Category category);
    void delete(Category category);
}
