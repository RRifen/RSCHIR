package com.rschir.prac.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.rschir.prac.util.enums.ProductType;
import com.rschir.prac.util.views.Views;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "telephone")
public class Telephone {

    @Id
    @Column(name = "telephone_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({Views.Get.class})
    private long telephoneId;

    @Column(name = "manufacturer", nullable = false)
    @JsonView({Views.Post.class})
    private String manufacturer;

    @Column(name = "battery_capacity", nullable = false)
    @JsonView({Views.Post.class})
    private int batteryCapacity;

    @Column(name = "seller_number", nullable = false)
    @JsonView({Views.Post.class})
    private long sellerNumber;

    @Column(name = "cost", nullable = false)
    @JsonView({Views.Post.class})
    private double cost;

    @Column(name = "name", nullable = false)
    @JsonView({Views.Post.class})
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    @JsonView({Views.Get.class})
    private ProductType productType;

}