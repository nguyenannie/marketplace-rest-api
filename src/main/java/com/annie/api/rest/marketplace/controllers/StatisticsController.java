package com.annie.api.rest.marketplace.controllers;

import com.annie.api.rest.marketplace.models.entities.Product;
import com.annie.api.rest.marketplace.models.entities.Seller;
import com.annie.api.rest.marketplace.services.implementations.ProductDbServiceImpl;
import com.annie.api.rest.marketplace.services.implementations.SellerDbServiceImpl;
import com.annie.api.rest.marketplace.services.interfaces.ProductDbService;
import com.annie.api.rest.marketplace.services.interfaces.SellerDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
public class StatisticsController {

    private final ProductDbService productDbService;
    private final SellerDbService sellerDbService;

    @Autowired
    public StatisticsController(ProductDbServiceImpl productDbServiceImpl, SellerDbServiceImpl sellerDbServiceImpl) {
        this.productDbService = productDbServiceImpl;
        this.sellerDbService = sellerDbServiceImpl;
    }

    @GetMapping("/products/sales")
    public ResponseEntity<List<Product>> getProductsBySales(@RequestParam(value = "order") String order) {
        Optional<List<Product>> sortedProduct = sortList(order, productDbService.findAll(), Product::getSalesUnit);
        return toResponse(sortedProduct);
    }

    @GetMapping("/products/mostViewed5")
    public List<Product> getTop5MostViewedProducts() {
        List<Product> products = productDbService.findAll();
        products.sort(Comparator.comparing(Product::getTimesQueried).reversed());
        if (products.size() <= 5) {
            return products;
        }
        return products.subList(0, 5);
    }

    @GetMapping("/sellers/top5Revenue")
    public List<Seller> getTop5RevenueSellers() {
        List<Seller> sellers = sellerDbService.findAll();
        sellers.sort(Comparator.comparing(Seller::getRevenue).reversed());
        if (sellers.size() < 5) {
            return sellers;
        }
        return sellers.subList(0, 5);
    }

    @GetMapping("/sellers/rating")
    public ResponseEntity<List<Seller>> getSellersByRating(@RequestParam(value = "order") String order) {
        Optional<List<Seller>> sortedSellers = sortList(order, sellerDbService.findAll(), Seller::getAverageRating);
        return toResponse(sortedSellers);
    }

    private <T> Optional<List<T>> sortList(String order, List<T> list, Function<T, Integer> keyExtractor) {
        Comparator<T> c;
        if (order.equals("asc")) {
            c = Comparator.comparing(keyExtractor);
        } else if (order.equals("desc")) {
            c = Comparator.comparing(keyExtractor).reversed();
        } else {
            return Optional.empty();
        }
        list.sort(c);
        return Optional.of(list);
    }

    private <T> ResponseEntity<T> toResponse(Optional<T> valueOpt) {
        return valueOpt
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
