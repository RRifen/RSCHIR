package com.rschir.prac.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.rschir.prac.util.views.Views;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@IdClass(CartItemId.class)
@Table(name = "cart_items")
public class CartItem {

    @Id
    @JsonView({Views.Get.class})
    private Long cartId;

    @Id
    private Long productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", nullable = false)
    private Product product;
}
