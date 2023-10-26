package com.rschir.prac.repositories;

import com.rschir.prac.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Product, Long> {
    List<Product> findAllBySellerNumber(Long sellerNumber);
}
