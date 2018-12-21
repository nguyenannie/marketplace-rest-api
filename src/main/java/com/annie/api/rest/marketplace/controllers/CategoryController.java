package com.annie.api.rest.marketplace.controllers;

import com.annie.api.rest.marketplace.models.entities.Category;
import com.annie.api.rest.marketplace.services.implementations.CategoryDbServiceImpl;
import com.annie.api.rest.marketplace.services.interfaces.CategoryDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CategoryController {

    private final CategoryDbService categoryDbService;

    @Autowired
    public CategoryController(CategoryDbServiceImpl categoryDbServiceImpl) {
        this.categoryDbService = categoryDbServiceImpl;
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryDbService.findAll();
    }

    @GetMapping("/category")
    public Category getCategory(@RequestParam(value = "name") String name) {
        return categoryDbService.findByName(name);
    }

    @PostMapping("/category")
    public Category saveCategory(@RequestParam(value = "name") String name) {
        Category categoryToBeSaved = Category.builder().name(name).build();
        categoryDbService.save(categoryToBeSaved);
        return categoryToBeSaved;
    }
}
