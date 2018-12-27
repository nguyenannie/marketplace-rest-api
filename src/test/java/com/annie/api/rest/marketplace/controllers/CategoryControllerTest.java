package com.annie.api.rest.marketplace.controllers;

import com.annie.api.rest.marketplace.models.entities.Category;
import com.annie.api.rest.marketplace.services.implementations.CategoryDbServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryDbServiceImpl categoryDbService;

    private Category category1;
    private Category category2;
    private List<Category> allCategories;

    @Before
    public void setUp() throws Exception {

        category1 = Category.builder()
                .id(1)
                .name("cat")
                .build();
        category2 = Category.builder()
                .id(2)
                .name("dog")
                .build();

        allCategories = new ArrayList<>(Arrays.asList(category1, category2));
    }

    @Test
    public void getAllCategories() throws Exception {
        given(categoryDbService.findAll()).willReturn(allCategories);

        mockMvc.perform(get("/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(category1.getName())))
                .andExpect(jsonPath("$[1].name", is(category2.getName())));
    }

    @Test
    public void getCategory_category_exists() throws Exception {
        given(categoryDbService.findByName(category1.getName())).willReturn(category1);

        mockMvc.perform(get("/category/cat")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("name", is(category1.getName())))
                .andExpect(jsonPath("id", is((int)category1.getId())));
    }

    @Test
    public void getCategory_category_doesnt_exists() throws Exception {
        given(categoryDbService.findByName("non-existent")).willReturn(null);

        mockMvc.perform(get("/category/non-existent")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void saveCategory_successful() throws Exception {
        given(categoryDbService.findByName(category2.getName())).willReturn(null);

        mockMvc.perform(post("/category/dog")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("name", is(category2.getName())));

    }

    @Test
    public void saveCategory_category_already_exists() throws Exception {
        given(categoryDbService.findByName(category2.getName())).willReturn(category2);

        mockMvc.perform(post("/category/dog")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void deleteCategory_successful() throws Exception {
        given(categoryDbService.findByName(category2.getName())).willReturn(category2);

        mockMvc.perform(delete("/category/dog")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("name", is(category2.getName())));
    }

    @Test
    public void deleteCategory_category_doesnt_exist() throws Exception {
        given(categoryDbService.findByName("non-existent")).willReturn(null);

        mockMvc.perform(delete("/category/non-existent")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
