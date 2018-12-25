package com.annie.api.rest.marketplace.controllers;

import com.annie.api.rest.marketplace.models.dtos.ApiError;
import com.annie.api.rest.marketplace.models.entities.Category;
import com.annie.api.rest.marketplace.services.implementations.CategoryDbServiceImpl;
import com.annie.api.rest.marketplace.services.interfaces.CategoryDbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Api(description = "Category Operations", produces = "application/json", tags = { "Category" })
public class CategoryController {

    private final CategoryDbService categoryDbService;

    @Autowired
    public CategoryController(CategoryDbServiceImpl categoryDbServiceImpl) {
        this.categoryDbService = categoryDbServiceImpl;
    }

    @ApiOperation(value = "Get All Categories", tags = { "Category" })
    @GetMapping(value = "/categories", produces = "application/json")
    public List<Category> getAllCategories() {
        return categoryDbService.findAll();
    }

    @ApiOperation(value = "Get Category", tags = { "Category" })
    @GetMapping(value = "/category/{name}", produces = "application/json")
    public Category getCategory(@PathVariable String name) {
        return categoryDbService.findByName(name);
    }

    @ApiOperation(value = "Create Category", tags = { "Category" })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Category.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiError.class)
    })
    @PostMapping(value = "/category/{name}", produces = "application/json")
    public ResponseEntity<Object> saveCategory(@PathVariable String name) {
        if (categoryDbService.findByName(name) != null) {
            return new ResponseEntity<>(new ApiError("Category name already exists", LocalDateTime.now()),
                    HttpStatus.BAD_REQUEST);
        }
        Category categoryToBeSaved = Category.builder().name(name).build();
        categoryDbService.save(categoryToBeSaved);
        return new ResponseEntity<>(categoryToBeSaved, HttpStatus.CREATED);
    }
}
