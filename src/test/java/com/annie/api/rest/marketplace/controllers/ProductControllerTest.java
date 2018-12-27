package com.annie.api.rest.marketplace.controllers;

import com.annie.api.rest.marketplace.models.dtos.ProductDTO;
import com.annie.api.rest.marketplace.models.entities.Category;
import com.annie.api.rest.marketplace.models.entities.Product;
import com.annie.api.rest.marketplace.models.entities.Seller;
import com.annie.api.rest.marketplace.services.implementations.CategoryDbServiceImpl;
import com.annie.api.rest.marketplace.services.implementations.ProductDbServiceImpl;
import com.annie.api.rest.marketplace.services.implementations.SellerDbServiceImpl;
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

import static com.annie.api.rest.marketplace.TestUtil.mapToJson;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductDbServiceImpl productDbService;

    @MockBean
    private SellerDbServiceImpl sellerDbService;

    @MockBean
    private CategoryDbServiceImpl categoryDbService;

    private Product product1;
    private Product product2;
    private Product product3;
    private List<Product> allProducts;
    private List<Product> seller1Products;
    private Category category1;
    private Category category2;
    private Seller seller1;
    private Seller seller2;
    private ProductDTO validProductDTO;
    private ProductDTO invalidProductDTO;

    @Before
    public void setUp() throws Exception {
        category1 = Category.builder().id(1).name("cat").build();
        category2 = Category.builder().id(2).name("dog").build();

        seller1 = Seller.builder()
                .id(1)
                .firstName("Neil")
                .lastName("Langley")
                .email("dapibus@maurisipsumporta.ca")
                .phoneNumber("0860 028 7320")
                .address("686-7190 Odio Rd.")
                .build();
        seller2 = Seller.builder()
                .id(2)
                .firstName("Nash")
                .lastName("Figueroa")
                .email("Nunc@dictum.co.uk")
                .phoneNumber("07624 847184")
                .address("4301 Quisque Av.")
                .build();

        product1 = Product.builder()
                .id(1)
                .name("Cat Toy")
                .description("Toy for Cats")
                .price(270)
                .seller(seller1)
                .category(category1)
                .stock(15)
                .build();

        product3 = Product.builder()
                .id(2)
                .name("Cat Food")
                .description("Food for Cats")
                .price(270)
                .seller(seller1)
                .category(category1)
                .stock(15)
                .build();

        product2 = Product.builder()
                .id(0)
                .name("Dog Toy")
                .description("Toy for Dogs")
                .price(270)
                .seller(seller2)
                .category(category2)
                .stock(15)
                .build();

        allProducts = new ArrayList<>(Arrays.asList(product1, product2));
        seller1Products = new ArrayList<>(Arrays.asList(product1, product3));

        seller1.setProducts(seller1Products);

        validProductDTO = new ProductDTO(product2.getName(), seller1.getId(), product2.getDescription(), product2.getStock(), category1.getName(), product2.getPrice());
        invalidProductDTO = new ProductDTO("", seller1.getId(), product2.getDescription(), -3, category1.getName(), product2.getPrice());
    }

    @Test
    public void getAllProducts() throws Exception {
        given(productDbService.findAll()).willReturn(allProducts);

        mockMvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(product1.getName())))
                .andExpect(jsonPath("$[0].description", is(product1.getDescription())))
                .andExpect(jsonPath("$[0].price", is(product1.getPrice())))
                .andExpect(jsonPath("$[0].stock", is(product1.getStock())))
                .andExpect(jsonPath("$[1].name", is(product2.getName())))
                .andExpect(jsonPath("$[1].description", is(product2.getDescription())))
                .andExpect(jsonPath("$[1].price", is(product2.getPrice())))
                .andExpect(jsonPath("$[1].stock", is(product2.getStock())));
    }

    @Test
    public void saveProduct_successful() throws Exception {
        given(categoryDbService.findByName(category1.getName())).willReturn(category1);
        given(sellerDbService.findById(seller1.getId())).willReturn(seller1);

        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(validProductDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(mapToJson(product2)));
    }

    @Test
    public void saveProduct_requestBody_is_invalid() throws Exception {
        given(categoryDbService.findByName(category1.getName())).willReturn(category1);
        given(sellerDbService.findById(seller1.getId())).willReturn(seller1);

        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(invalidProductDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveProduct_seller_doesnt_exist() throws Exception {
        given(categoryDbService.findByName(category1.getName())).willReturn(category1);
        given(sellerDbService.findById(3)).willReturn(null);

        validProductDTO.setSellerId(3);

        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(validProductDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveProduct_category_doesnt_exist() throws Exception {
        given(categoryDbService.findByName("non-existent")).willReturn(null);
        given(sellerDbService.findById(seller1.getId())).willReturn(seller1);

        validProductDTO.setCategoryName("non-existent");

        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(validProductDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getProductsBySeller_successful() throws Exception {
        given(sellerDbService.findById(seller1.getId())).willReturn(seller1);

        mockMvc.perform(get("/products/seller/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(product1.getName())))
                .andExpect(jsonPath("$[0].description", is(product1.getDescription())))
                .andExpect(jsonPath("$[0].categoryName", is(product1.getCategory().getName())))
                .andExpect(jsonPath("$[1].name", is(product3.getName())))
                .andExpect(jsonPath("$[1].description", is(product3.getDescription())))
                .andExpect(jsonPath("$[1].categoryName", is(product3.getCategory().getName())));
    }

    @Test
    public void getProductsBySeller_seller_doesnt_exist() throws Exception {
        given(sellerDbService.findById(4)).willReturn(null);

        mockMvc.perform(get("/products/seller/4")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getProductById_successful() throws Exception {
        given(productDbService.findById(product1.getId())).willReturn(product1);

        mockMvc.perform(get("/product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(mapToJson(product1)));
    }

    @Test
    public void getProductById_product_doesnt_exist() throws Exception {
        given(productDbService.findById(4)).willReturn(null);

        mockMvc.perform(get("/product/4")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteProductById_successful() throws Exception {
        given(productDbService.findById(product1.getId())).willReturn(product1);

        mockMvc.perform(delete("/product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(mapToJson(product1)));
    }

    @Test
    public void deleteProductById_product_doesnt_exist() throws Exception {
        given(productDbService.findById(4)).willReturn(null);

        mockMvc.perform(delete("/product/4")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void modifyProduct_successful() throws Exception {
        given(productDbService.findById(product1.getId())).willReturn(product1);
        given(categoryDbService.findByName(category1.getName())).willReturn(category1);
        given(sellerDbService.findById(seller1.getId())).willReturn(seller1);

        mockMvc.perform(put("/product/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(validProductDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("name", is(product2.getName())))
                .andExpect(jsonPath("description", is(product2.getDescription())))
                .andExpect(jsonPath("price", is(product2.getPrice())))
                .andExpect(jsonPath("stock", is(product2.getStock())));
    }

    @Test
    public void modifyProduct_product_doesnt_exist() throws Exception {
        given(productDbService.findById(4)).willReturn(null);
        given(categoryDbService.findByName(category1.getName())).willReturn(category1);
        given(sellerDbService.findById(seller1.getId())).willReturn(seller1);

        mockMvc.perform(put("/product/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(validProductDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void modifyProduct_requestBody_is_invalid() throws Exception {
        given(productDbService.findById(product1.getId())).willReturn(product1);
        given(categoryDbService.findByName(category1.getName())).willReturn(category1);
        given(sellerDbService.findById(seller1.getId())).willReturn(seller1);

        mockMvc.perform(put("/product/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(invalidProductDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void modifyProduct_category_doesnt_exist() throws Exception {
        given(productDbService.findById(product1.getId())).willReturn(product1);
        given(categoryDbService.findByName("non-existent")).willReturn(null);
        given(sellerDbService.findById(seller1.getId())).willReturn(seller1);

        validProductDTO.setCategoryName("non-existent");

        mockMvc.perform(put("/product/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(validProductDTO)))
                .andExpect(status().isBadRequest());
    }
}
