package com.annie.api.rest.marketplace.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private long sellerId;
    private String description;
    private int stock;
    private String categoryName;
    private int price;

    @JsonIgnore
    public boolean isValid() {
        return this.name != null && !this.name.isEmpty() &&
                this.stock > 0 && this.categoryName != null;
    }
}
