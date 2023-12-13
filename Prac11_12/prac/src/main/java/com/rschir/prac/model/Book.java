package com.rschir.prac.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rschir.prac.util.enums.ProductType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("book_id")
    private Long bookId;

    private String author;

    @Column(name = "seller_number", nullable = false)
    @JsonProperty("seller_number")
    private Long sellerNumber;

    @Column(name = "cost", nullable = false)
    private double cost;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    @JsonProperty("product_type")
    private ProductType productType;
}
