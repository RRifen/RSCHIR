package com.rschir.prac.model;

import com.rschir.prac.util.enums.ProductType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "product_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "seller_number", nullable = false)
    private Long sellerNumber;

    @Column(name = "cost", nullable = false)
    private double cost;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "count", nullable = false)
    private int count;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    private ProductType productType;
}
