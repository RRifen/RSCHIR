package com.rschir.prac.repositories;

import com.rschir.prac.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartsRepository extends JpaRepository<Cart, Long> {
}
