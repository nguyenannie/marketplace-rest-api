package com.annie.api.rest.marketplace.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "seller")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    private String address;
    private String phoneNumber;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seller")
    @JsonManagedReference
    private List<Product> products;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seller")
    @JsonManagedReference
    private List<Rating> ratings;
}
