package com.annie.api.rest.marketplace.models.dtos;

import com.annie.api.rest.marketplace.models.entities.Seller;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class SellerStatDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private int salesUnit;
    private int revenue;

    public SellerStatDTO(Seller seller) {
        this.id = seller.getId();
        this.firstName = seller.getFirstName();
        this.lastName = seller.getLastName();
        this.email = seller.getEmail();
        this.salesUnit = seller.getSalesUnit();
        this.revenue = seller.getRevenue();
    }
}
