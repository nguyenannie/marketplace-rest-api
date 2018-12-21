package com.annie.api.rest.marketplace.controllers;

import com.annie.api.rest.marketplace.models.dtos.ApiError;
import com.annie.api.rest.marketplace.models.dtos.ProductDTO;
import com.annie.api.rest.marketplace.models.entities.Category;
import com.annie.api.rest.marketplace.models.entities.Product;
import com.annie.api.rest.marketplace.models.entities.Seller;
import com.annie.api.rest.marketplace.services.implementations.CategoryDbServiceImpl;
import com.annie.api.rest.marketplace.services.implementations.ProductDbServiceImpl;
import com.annie.api.rest.marketplace.services.implementations.SellerDbServiceImpl;
import com.annie.api.rest.marketplace.services.interfaces.CategoryDbService;
import com.annie.api.rest.marketplace.services.interfaces.ProductDbService;
import com.annie.api.rest.marketplace.services.interfaces.SellerDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ProductController {

    private final ProductDbService productDbService;
    private final SellerDbService sellerDbService;
    private final CategoryDbService categoryDbService;

    @Autowired
    public ProductController(ProductDbServiceImpl productDbServiceImpl,
                             SellerDbServiceImpl sellerDbServiceImpl,
                             CategoryDbServiceImpl categoryDbServiceImpl) {
        this.productDbService = productDbServiceImpl;
        this.sellerDbService = sellerDbServiceImpl;
        this.categoryDbService = categoryDbServiceImpl;
    }

    @PostMapping("/product")
    public ResponseEntity<Object> saveOneProduct(@RequestBody ProductDTO productDTO) {
        if (!productDTO.isValid()) {
            return new ResponseEntity<>(new ApiError("Invalid request body, please check the documentation!",
                    LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }
        Seller seller = sellerDbService.findById(productDTO.getSellerId());
        if (seller == null) {
            return new ResponseEntity<>(new ApiError("Seller doesn't exist", LocalDateTime.now()),
                    HttpStatus.BAD_REQUEST);
        }
        Category category = categoryDbService.findByName(productDTO.getCategoryName());
        if (category == null) {
            return new ResponseEntity<>(new ApiError("Category doesn't exist", LocalDateTime.now()),
                    HttpStatus.BAD_REQUEST);
        }
        Product productToBeSaved = Product.builder()
                .name(productDTO.getName())
                .seller(seller)
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .category(category)
                .stock(productDTO.getStock()).build();
        productDbService.save(productToBeSaved);
        sellerDbService.save(seller);
        categoryDbService.save(category);
        return new ResponseEntity<>(productToBeSaved, HttpStatus.CREATED);
    }

    @GetMapping("/products/all")
    public List<Product> getAllProducts() {
        return productDbService.findAll();
    }

    @GetMapping("/products/seller")
    public ResponseEntity<List<Product>> getProductsBySeller(@RequestParam(value = "id") long sellerId) {
        Seller seller = sellerDbService.findById(sellerId);
        if (seller == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(seller.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/product")
    public ResponseEntity<Product> getProductById(@RequestParam(value = "id") long id) {
        Product productToGet = productDbService.findById(id);
        if (productToGet == null) {
            return ResponseEntity.notFound().build();
        }
        productToGet.setTimesQueried(productToGet.getTimesQueried() + 1);
        productDbService.save(productToGet);
        return new ResponseEntity<>(productToGet, HttpStatus.OK);
    }

    @DeleteMapping("/product")
    public ResponseEntity<Product> deleteProductById(@RequestParam(value = "id") long id) {
        Product productToDelete = productDbService.findById(id);
        if (productToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        productDbService.delete(id);
        return new ResponseEntity<>(productToDelete, HttpStatus.OK);
    }

    @PutMapping("/product")
    public ResponseEntity<Object> modifyProductById(@RequestParam(value = "id") long id,
                                                     @RequestBody ProductDTO productDTO) {
        Product productToModify = productDbService.findById(id);
        Category categoryToModify = categoryDbService.findByName(productDTO.getCategoryName());
        if (productToModify == null) {
            return ResponseEntity.notFound().build();
        }
        if (!productDTO.isValid() || categoryToModify == null) {
            return new ResponseEntity<>(new ApiError("Invalid request body, please check the documentation!",
                    LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }
        productToModify.setDescription(productDTO.getDescription());
        productToModify.setName(productDTO.getName());
        productToModify.setStock(productDTO.getStock());
        productToModify.setPrice(productDTO.getPrice());
        productToModify.setCategory(categoryToModify);

        productDbService.save(productToModify);
        categoryDbService.save(categoryToModify);

        return new ResponseEntity<>(productToModify, HttpStatus.CREATED);
    }
}
