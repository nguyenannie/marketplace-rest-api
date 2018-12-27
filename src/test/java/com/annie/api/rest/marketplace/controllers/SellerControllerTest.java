package com.annie.api.rest.marketplace.controllers;

import com.annie.api.rest.marketplace.models.dtos.SellerDTO;
import com.annie.api.rest.marketplace.models.entities.Seller;
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
@WebMvcTest(SellerController.class)
public class SellerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SellerDbServiceImpl sellerDbService;

    private Seller seller1;
    private Seller seller2;
    private List<Seller> allSellers;
    private SellerDTO validSellerDTO;
    private SellerDTO invalidSellerDTO;

    @Before
    public void setUp() throws Exception {
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
        allSellers = new ArrayList<>(Arrays.asList(seller1, seller2));

        validSellerDTO = new SellerDTO("Branden", "Cervantes", "at@Crasloremlorem.edu", "070 4516 7493", "150-2570 Gravida Av.");
        invalidSellerDTO = new SellerDTO("", "", "", "", "");
    }

    @Test
    public void getAllSellers() throws Exception {
        given(sellerDbService.findAll()).willReturn(allSellers);

        mockMvc.perform(get("/sellers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is(seller1.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(seller1.getLastName())))
                .andExpect(jsonPath("$[0].email", is(seller1.getEmail())))
                .andExpect(jsonPath("$[0].phoneNumber", is(seller1.getPhoneNumber())))
                .andExpect(jsonPath("$[0].address", is(seller1.getAddress())))
                .andExpect(jsonPath("$[1].firstName", is(seller2.getFirstName())))
                .andExpect(jsonPath("$[1].lastName", is(seller2.getLastName())))
                .andExpect(jsonPath("$[1].email", is(seller2.getEmail())))
                .andExpect(jsonPath("$[1].phoneNumber", is(seller2.getPhoneNumber())))
                .andExpect(jsonPath("$[1].address", is(seller2.getAddress())));
    }

    @Test
    public void getSellerById_when_id_exists() throws Exception {
        given(sellerDbService.findById(seller1.getId())).willReturn(seller1);

        mockMvc.perform(get("/seller/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(mapToJson(seller1)));
    }

    @Test
    public void getSellerById_when_id_doesnt_exist() throws Exception {
        given(sellerDbService.findById(6)).willReturn(null);

        mockMvc.perform(get("/seller/6")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void saveSeller_when_requestBody_is_valid() throws Exception {
        mockMvc.perform(post("/seller")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(validSellerDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(mapToJson(validSellerDTO)));
    }

    @Test
    public void saveSeller_when_requestBody_is_not_valid() throws Exception {
        mockMvc.perform(post("/seller")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(invalidSellerDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteSeller_when_id_exist() throws Exception {
        given(sellerDbService.findById(2)).willReturn(seller2);

        mockMvc.perform(delete("/seller/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(mapToJson(seller2)));
    }

    @Test
    public void deleteSeller_when_id_doesnt_exist() throws Exception {
        given(sellerDbService.findById(6)).willReturn(null);

        mockMvc.perform(delete("/seller/6")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void modifySeller_successful() throws Exception {
        given(sellerDbService.findById(1)).willReturn(seller1);

        mockMvc.perform(put("/seller/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(validSellerDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(mapToJson(validSellerDTO)));
    }

    @Test
    public void modifySeller_when_id_doesnt_exist() throws Exception {
        given(sellerDbService.findById(5)).willReturn(null);

        mockMvc.perform(put("/seller/6")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(validSellerDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void modifySeller_when_requestBody_is_invalid() throws Exception {
        given(sellerDbService.findById(1)).willReturn(seller1);

        mockMvc.perform(put("/seller/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(invalidSellerDTO)))
                .andExpect(status().isBadRequest());
    }

}
