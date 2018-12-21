package com.annie.api.rest.marketplace.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RatingDTO {
    private int rate;
    private String feedback;
}
