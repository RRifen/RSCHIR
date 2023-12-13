package com.rschir.prac.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rschir.prac.util.enums.ProductType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "washing_machines")
public class WashingMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("washing_machine_id")
    private Long washingMachineId;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "tank_volume", nullable = false)
    @JsonProperty("tank_volume")
    private int tankVolume;

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
