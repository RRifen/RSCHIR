package com.rschir.prac.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class CartItemId implements Serializable {
    private Long clientId;
    private Long productId;
}
