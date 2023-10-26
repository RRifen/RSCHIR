package com.rschir.prac.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "washing_machines")
public class WashingMachine {

    @Id
    private Long productId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "tank_volume", nullable = false)
    private int tankVolume;

}
