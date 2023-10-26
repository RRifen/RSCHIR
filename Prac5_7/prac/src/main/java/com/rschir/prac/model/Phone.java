package com.rschir.prac.model;

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
    private Long productId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "battery_capacity", nullable = false)
    private int batteryCapacity;


}
