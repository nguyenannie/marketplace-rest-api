package com.annie.api.rest.marketplace.controllers;

import com.annie.api.rest.marketplace.models.dtos.RatingDTO;
import com.annie.api.rest.marketplace.models.entities.Rating;
import com.annie.api.rest.marketplace.models.entities.Seller;
import com.annie.api.rest.marketplace.services.implementations.RatingDbServiceImpl;
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

import java.util.Arrays;

import static com.annie.api.rest.marketplace.TestUtil.mapToJson;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RateController.class)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SellerDbServiceImpl sellerDbService;

    @MockBean
    private RatingDbServiceImpl ratingDbService;

    private Rating rating1, rating2, rating3, rating4;
    private Seller seller1;
    private Seller seller2;

    @Before
    public void setUp() throws Exception {
        rating1 = Rating.builder()
                .id(1)
                .rate(3)
                .build();

        rating2 = Rating.builder()
                .id(2)
                .rate(4)
                .build();

        rating3 = Rating.builder()
                .id(3)
                .rate(2)
                .build();

        rating4 = Rating.builder()
                .id(4)
                .rate(3)
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

        rating1.setSeller(seller1);
        rating2.setSeller(seller1);
        rating3.setSeller(seller2);
        rating4.setSeller(seller2);
    }

    @Test
    public void getRatingsBySeller_seller_exists() throws Exception {
        given(sellerDbService.findById(seller1.getId())).willReturn(seller1);

        mockMvc.perform(get("/rating/seller/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].rate", is(rating1.getRate())))
                .andExpect(jsonPath("$[1].rate", is(rating2.getRate())));
    }

    @Test
    public void getRatingsBySeller_seller_doesnt_exists() throws Exception {
        given(sellerDbService.findById(5)).willReturn(null);

        mockMvc.perform(get("/rating/seller/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void rateSeller_successful() throws Exception {
        given(sellerDbService.findById(seller1.getId())).willReturn(seller1);

        mockMvc.perform(post("/rating/seller/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(new RatingDTO(4, "nice"))))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("rate", is(4)))
                .andExpect(jsonPath("feedback", is("nice")));
    }

    @Test
    public void rateSeller_seller_doesnt_exists() throws Exception {
        given(sellerDbService.findById(5)).willReturn(null);

        mockMvc.perform(post("/rating/seller/5")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(new RatingDTO(4, "nice"))))
                .andExpect(status().isNotFound());
    }

    @Test
    public void rateSeller_invalid_rating() throws Exception {
        given(sellerDbService.findById(seller1.getId())).willReturn(seller1);

        mockMvc.perform(post("/rating/seller/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(new RatingDTO(7, "nice"))))
                .andExpect(status().isBadRequest());
    }
}
