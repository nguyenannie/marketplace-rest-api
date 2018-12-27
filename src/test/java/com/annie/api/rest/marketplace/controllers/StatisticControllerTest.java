package com.annie.api.rest.marketplace.controllers;

import com.annie.api.rest.marketplace.models.dtos.ProductDTO;
import com.annie.api.rest.marketplace.models.entities.Category;
import com.annie.api.rest.marketplace.models.entities.Product;
import com.annie.api.rest.marketplace.models.entities.Rating;
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
import java.util.Collections;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticsController.class)
public class StatisticControllerTest {

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
    private List<Category> allCategories;
    private Seller seller1;
    private Seller seller2;
    private List<Seller> allSellers;
    private ProductDTO validProductDTO;
    private ProductDTO invalidProductDTO;

    @Before
    public void setUp() throws Exception {
        Rating rating1 = Rating.builder()
                .seller(seller1)
                .rate(3)
                .build();

        Rating rating2 = Rating.builder()
                .seller(seller1)
                .rate(4)
                .build();

        Rating rating3 = Rating.builder()
                .seller(seller2)
                .rate(2)
                .build();

        Rating rating4 = Rating.builder()
                .seller(seller2)
                .rate(3)
                .build();
        category1 = Category.builder()
                .id(1)
                .name("cat")
                .products(Arrays.asList(product1, product3))
                .build();
        category2 = Category.builder()
                .id(2)
                .name("dog")
                .products(Collections.singletonList(product2))
                .build();

        seller1 = Seller.builder()
                .id(1)
                .firstName("Neil")
                .lastName("Langley")
                .email("dapibus@maurisipsumporta.ca")
                .phoneNumber("0860 028 7320")
                .address("686-7190 Odio Rd.")
                .ratings(Arrays.asList(rating1, rating2))
                .build();
        seller2 = Seller.builder()
                .id(2)
                .firstName("Nash")
                .lastName("Figueroa")
                .email("Nunc@dictum.co.uk")
                .phoneNumber("07624 847184")
                .address("4301 Quisque Av.")
                .ratings(Arrays.asList(rating3, rating4))
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

        allSellers = new ArrayList<>(Arrays.asList(seller1, seller2));
        allProducts = new ArrayList<>(Arrays.asList(product1, product2));
        allCategories = new ArrayList<>(Arrays.asList(category1, category2));
        seller1Products = new ArrayList<>(Arrays.asList(product1, product3));

        seller1.setProducts(seller1Products);

        validProductDTO = new ProductDTO(product2.getName(), seller1.getId(), product2.getDescription(), product2.getStock(), category1.getName(), product2.getPrice());
        invalidProductDTO = new ProductDTO("", seller1.getId(), product2.getDescription(), -3, category1.getName(), product2.getPrice());
    }

    @Test
    public void getProductsWithSalesData() throws Exception {
        given(productDbService.findAll()).willReturn(allProducts);

        mockMvc.perform(get("/stat/products/salesData")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(product1.getName())))
                .andExpect(jsonPath("$[0].description", is(product1.getDescription())))
                .andExpect(jsonPath("$[0].categoryName", is(product1.getCategory().getName())))
                .andExpect(jsonPath("$[0].salesUnit", is(product1.getSalesUnit())))
                .andExpect(jsonPath("$[0].revenue", is(product1.getRevenue())))
                .andExpect(jsonPath("$[1].name", is(product2.getName())))
                .andExpect(jsonPath("$[1].description", is(product2.getDescription())))
                .andExpect(jsonPath("$[1].categoryName", is(product2.getCategory().getName())))
                .andExpect(jsonPath("$[1].salesUnit", is(product2.getSalesUnit())))
                .andExpect(jsonPath("$[1].revenue", is(product2.getRevenue())));
    }

    @Test
    public void getProductsBySales_order_is_asc() throws Exception {
        given(productDbService.findAll()).willReturn(allProducts);
        product1.setSalesUnit(5);
        product2.setSalesUnit(7);
        mockMvc.perform(get("/stat/products/sales?order=asc")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(product1.getName())))
                .andExpect(jsonPath("$[1].name", is(product2.getName())));
    }

    @Test
    public void getProductsBySales_order_is_desc() throws Exception {
        given(productDbService.findAll()).willReturn(allProducts);
        product1.setSalesUnit(5);
        product2.setSalesUnit(7);
        mockMvc.perform(get("/stat/products/sales?order=desc")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(product2.getName())))
                .andExpect(jsonPath("$[1].name", is(product1.getName())));
    }

    @Test
    public void getProductsBySales_order_param_is_invalid() throws Exception {
        given(productDbService.findAll()).willReturn(allProducts);
        product1.setSalesUnit(5);
        product2.setSalesUnit(7);
        mockMvc.perform(get("/stat/products/sales?order=something")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getListSellersWithSalesData() throws Exception {
        given(sellerDbService.findAll()).willReturn(allSellers);

        mockMvc.perform(get("/stat/sellers/salesData")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is(seller1.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(seller1.getLastName())))
                .andExpect(jsonPath("$[0].email", is(seller1.getEmail())))
                .andExpect(jsonPath("$[0].averageRating", is(seller1.getAverageRating())))
                .andExpect(jsonPath("$[0].salesUnit", is(seller1.getSalesUnit())))
                .andExpect(jsonPath("$[0].revenue", is(seller1.getRevenue())))
                .andExpect(jsonPath("$[1].firstName", is(seller2.getFirstName())))
                .andExpect(jsonPath("$[1].lastName", is(seller2.getLastName())))
                .andExpect(jsonPath("$[1].email", is(seller2.getEmail())))
                .andExpect(jsonPath("$[1].averageRating", is(seller2.getAverageRating())))
                .andExpect(jsonPath("$[1].salesUnit", is(seller2.getSalesUnit())))
                .andExpect(jsonPath("$[1].revenue", is(seller2.getRevenue())));
    }

    @Test
    public void getSellersByRating_order_is_asc() throws Exception {
        given(sellerDbService.findAll()).willReturn(allSellers);
        mockMvc.perform(get("/stat/sellers/rating?order=asc")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is((int)seller2.getId())))
                .andExpect(jsonPath("$[1].id", is((int)seller1.getId())));
    }

    @Test
    public void getSellersByRating_order_is_desc() throws Exception {
        given(sellerDbService.findAll()).willReturn(allSellers);
        mockMvc.perform(get("/stat/sellers/rating?order=desc")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is((int)seller1.getId())))
                .andExpect(jsonPath("$[1].id", is((int)seller2.getId())));
    }

    @Test
    public void getSellersByRating_order_param_is_invalid() throws Exception {
        given(sellerDbService.findAll()).willReturn(allSellers);
        mockMvc.perform(get("/stat/sellers/rating?order=something")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void listSalesPerCategory() throws Exception {
        product1.setSalesUnit(3);
        product2.setSalesUnit(4);
        product3.setSalesUnit(2);

        category1.setProducts(Arrays.asList(product1, product3));
        category2.setProducts(Collections.singletonList(product2));

        given(categoryDbService.findAll()).willReturn(allCategories);
        mockMvc.perform(get("/stat/salesPerCategory")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(category1.getName())))
                .andExpect(jsonPath("$[0].revenue",
                        is(category1.getProducts().stream().mapToInt(Product::getRevenue).sum())))
                .andExpect(jsonPath("$[1].name", is(category2.getName())))
                .andExpect(jsonPath("$[1].revenue",
                        is(category2.getProducts().stream().mapToInt(Product::getRevenue).sum())));
    }
}
