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
@Table(name = "washing_machines")
public class WashingMachine {

    @Id
    @JsonView({Views.Get.class})
    private Long productId;

    @MapsId
    @OneToOne
    @JsonView({Views.Post.class})
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "manufacturer", nullable = false)
    @JsonView({Views.Post.class})
    private String manufacturer;

    @Column(name = "tank_volume", nullable = false)
    @JsonView({Views.Post.class})
    private int tankVolume;

}
