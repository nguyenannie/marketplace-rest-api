package com.annie.api.rest.marketplace.controllers;

import com.annie.api.rest.marketplace.models.dtos.CategoryStatDTO;
import com.annie.api.rest.marketplace.models.dtos.ProductsStatDTO;
import com.annie.api.rest.marketplace.models.dtos.SellerStatDTO;
import com.annie.api.rest.marketplace.models.entities.Category;
import com.annie.api.rest.marketplace.models.entities.Product;
import com.annie.api.rest.marketplace.models.entities.Seller;
import com.annie.api.rest.marketplace.services.implementations.CategoryDbServiceImpl;
import com.annie.api.rest.marketplace.services.implementations.ProductDbServiceImpl;
import com.annie.api.rest.marketplace.services.implementations.SellerDbServiceImpl;
import com.annie.api.rest.marketplace.services.interfaces.CategoryDbService;
import com.annie.api.rest.marketplace.services.interfaces.ProductDbService;
import com.annie.api.rest.marketplace.services.interfaces.SellerDbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stat")
@Api(description = "Statistics Operations", produces = "application/json", tags = { "Statistics" })
public class StatisticsController {

    private final ProductDbService productDbService;
    private final SellerDbService sellerDbService;
    private final CategoryDbService categoryDbService;

    @Autowired
    public StatisticsController(ProductDbServiceImpl productDbServiceImpl,
                                SellerDbServiceImpl sellerDbServiceImpl,
                                CategoryDbServiceImpl categoryDbServiceImpl) {
        this.productDbService = productDbServiceImpl;
        this.sellerDbService = sellerDbServiceImpl;
        this.categoryDbService = categoryDbServiceImpl;
    }

    @ApiOperation(value = "List Products With Sales Data", tags = { "Statistics" })
    @GetMapping(value = "/products/salesData", produces = "application/json")
    public List<ProductsStatDTO> getProductsWithSalesData() {
        List<Product> products = (productDbService.findAll() == null)? new ArrayList<>() : productDbService.findAll();
        return products.stream().map(ProductsStatDTO::new).collect(Collectors.toList());
    }

    @ApiOperation(value = "Order List of Products By Sales", tags = { "Statistics" })
    @GetMapping(value = "/products/sales", produces = "application/json")
    public ResponseEntity<List<Product>> getProductsBySales(@RequestParam(value = "order") String order) {
        Optional<List<Product>> sortedProduct = sortList(order, productDbService.findAll(), Product::getSalesUnit);
        return toResponse(sortedProduct);
    }

    @ApiOperation(value = "List Sellers With Sales Data", tags = { "Statistics" })
    @GetMapping(value = "/sellers/salesData", produces = "application/json")
    public List<SellerStatDTO> getListSellersWithSalesData() {
        List<Seller> sellers = (sellerDbService.findAll() == null) ? new ArrayList<>() : sellerDbService.findAll();
        return sellers.stream().map(SellerStatDTO::new).collect(Collectors.toList());
    }

    @ApiOperation(value = "Order List of Sellers by Average Rating", tags = { "Statistics" })
    @GetMapping(value = "/sellers/rating", produces = "application/json")
    public ResponseEntity<List<SellerStatDTO>> getSellersByRating(@RequestParam(value = "order") String order) {
        List<Seller> sellers = (sellerDbService.findAll() == null) ? new ArrayList<>() : sellerDbService.findAll();
        List<SellerStatDTO> sellerStatDTOS = sellers.stream().map(SellerStatDTO::new).collect(Collectors.toList());
        Optional<List<SellerStatDTO>> sortedSellers = sortList(order, sellerStatDTOS, SellerStatDTO::getAverageRating);
        return toResponse(sortedSellers);
    }

    @ApiOperation(value = "List Top 5 Sellers by Total Revenue", tags = { "Statistics" })
    @GetMapping(value = "/sellers/top5Revenue", produces = "application/json")
    public List<SellerStatDTO> getTop5RevenueSellers() {
        List<Seller> sellers = (sellerDbService.findAll() == null) ? new ArrayList<>() : sellerDbService.findAll();
        sellers.sort(Comparator.comparing(Seller::getRevenue).reversed());
        List<SellerStatDTO> sellerSalesDTOS = sellers.stream().map(SellerStatDTO::new).collect(Collectors.toList());
        if (sellerSalesDTOS.size() < 5) {
            return sellerSalesDTOS;
        }
        return sellerSalesDTOS.subList(0, 5);
    }

    @ApiOperation(value = "List Top 5 Most Viewed Products", tags = { "Statistics" })
    @GetMapping(value = "/products/top5Viewed", produces = "application/json")
    public List<Product> getTop5MostViewedProducts() {
        List<Product> products = (productDbService.findAll() == null)? new ArrayList<>() : productDbService.findAll();
        products.sort(Comparator.comparing(Product::getTimesQueried).reversed());
        if (products.size() <= 5) {
            return products;
        }
        return products.subList(0, 5);
    }

    @ApiOperation(value = "List Total sales per product category", tags = { "Statistics" })
    @GetMapping(value = "/salesPerCategory", produces = "application/json")
    public List<CategoryStatDTO> listSalesPerCategory() {
        List<Category> categories = categoryDbService.findAll();
        return (categories == null) ?
                new ArrayList<>() : categories.stream().map(CategoryStatDTO::new).collect(Collectors.toList());
    }

    private <T, U extends Comparable<U>> Optional<List<T>> sortList(String order, List<T> list, Function<T, U> keyExtractor) {
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
