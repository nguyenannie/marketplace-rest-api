package com.annie.api.rest.marketplace.repositories;

import com.annie.api.rest.marketplace.models.entities.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends CrudRepository<Category, Long> {

    List<Category> findAll();
    Category findByName(String name);
}
