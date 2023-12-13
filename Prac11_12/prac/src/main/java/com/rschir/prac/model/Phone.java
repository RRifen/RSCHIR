package com.rschir.prac.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rschir.prac.util.enums.ProductType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "telephones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("phone_id")
    private Long phoneId;

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

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "battery_capacity", nullable = false)
    @JsonProperty("battery_capacity")
    private int batteryCapacity;


}
