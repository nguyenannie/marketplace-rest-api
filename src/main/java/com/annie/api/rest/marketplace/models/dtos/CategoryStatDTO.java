package com.annie.api.rest.marketplace.models.dtos;

import com.annie.api.rest.marketplace.models.entities.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryStatDTO {
    private long id;
    private String name;
    private int revenue;

    public CategoryStatDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.revenue = category.getRevenue();
    }
}
