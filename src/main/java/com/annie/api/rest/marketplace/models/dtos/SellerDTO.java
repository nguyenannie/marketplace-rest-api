package com.annie.api.rest.marketplace.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class SellerDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;

    @JsonIgnore
    public boolean isValid() {
        return firstName != null && !firstName.isEmpty() &&
                lastName != null && !lastName.isEmpty() &&
                email != null && !email.isEmpty();
    }
}
