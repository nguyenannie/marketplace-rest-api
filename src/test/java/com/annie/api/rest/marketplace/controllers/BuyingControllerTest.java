package com.annie.api.rest.marketplace.controllers;

import com.annie.api.rest.marketplace.models.entities.Category;
import com.annie.api.rest.marketplace.models.entities.Product;
import com.annie.api.rest.marketplace.models.entities.Seller;
import com.annie.api.rest.marketplace.services.implementations.ProductDbServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.core.Is.is;

import static com.annie.api.rest.marketplace.TestUtil.mapToJson;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BuyingController.class)
public class BuyingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductDbServiceImpl productDbService;

    private Product product;

    @Before
    public void setUp() throws Exception {
        product = Product.builder()
                .id(1)
                .name("Cat Toy")
                .description("Toy for Cats")
                .price(270)
                .seller(Seller.builder()
                        .id(1)
                        .firstName("Geoffrey")
                        .lastName("Gaines")
                        .email("purus.mauris@idsapien.org").build())
                .category(Category.builder().id(1).name("cat").build())
                .stock(15)
                .build();
    }

    @Test
    public void buyProduct_without_quantity_param_successful() throws Exception {
        given(productDbService.findById(1)).willReturn(product);
        int newSalesUnit = product.getSalesUnit() + 1;
        mockMvc.perform(post("/buy?productId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(mapToJson(product)))
                .andExpect(jsonPath("salesUnit", is(newSalesUnit)));
    }

    @Test
    public void buyProduct_with_quantity_param_successful() throws Exception {
        given(productDbService.findById(1)).willReturn(product);
        int newSalesUnit = product.getSalesUnit() + 3;
        mockMvc.perform(post("/buy?productId=1&quantity=3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(mapToJson(product)))
                .andExpect(jsonPath("salesUnit", is(newSalesUnit)));
    }

    @Test
    public void buyProduct_productId_doesnt_exist() throws Exception {
        given(productDbService.findById(3)).willReturn(null);
        mockMvc.perform(post("/buy?productId=3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void buyProduct_quantity_more_than_stock() throws Exception {
        given(productDbService.findById(1)).willReturn(product);
        mockMvc.perform(post("/buy?productId=1&quantity=16")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
