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
@Table(name = "books")
public class Book {

    @Id
    @JsonView({Views.Get.class})
    private Long productId;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    @JsonView({Views.Post.class})
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "author", nullable = false)
    @JsonView(Views.Post.class)
    private String author;
}
