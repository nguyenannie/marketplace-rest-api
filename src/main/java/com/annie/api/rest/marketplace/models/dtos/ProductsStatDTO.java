package com.annie.api.rest.marketplace.models.dtos;

import com.annie.api.rest.marketplace.models.entities.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductsStatDTO {
    private long id;
    private String name;
    private String description;
    private String categoryName;
    private int salesUnit;
    private int revenue;

    public ProductsStatDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.categoryName = product.getCategory().getName();
        this.salesUnit = product.getSalesUnit();
        this.revenue = product.getRevenue();
    }
}
