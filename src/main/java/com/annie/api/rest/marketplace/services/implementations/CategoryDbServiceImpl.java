package com.annie.api.rest.marketplace.services.implementations;

import com.annie.api.rest.marketplace.models.entities.Category;
import com.annie.api.rest.marketplace.repositories.CategoryRepo;
import com.annie.api.rest.marketplace.services.interfaces.CategoryDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryDbServiceImpl implements CategoryDbService {

    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryDbServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Category findByName(String name) {
        return categoryRepo.findByName(name);
    }

    @Override
    public void save(Category category) {
        categoryRepo.save(category);
    }

    @Override
    public void delete(Category category) {
        categoryRepo.delete(category);
    }
}
