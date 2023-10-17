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
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "product_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "seller_number", nullable = false)
    @JsonView(Views.Post.class)
    private Long sellerNumber;

    @Column(name = "cost", nullable = false)
    @JsonView({Views.Post.class})
    private double cost;

    @Column(name = "name", nullable = false)
    @JsonView(Views.Post.class)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    @JsonView(Views.Get.class)
    private ProductType productType;
}
