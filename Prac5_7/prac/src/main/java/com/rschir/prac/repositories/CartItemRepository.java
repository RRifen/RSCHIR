package com.rschir.prac.repositories;

import com.rschir.prac.model.CartItem;
import com.rschir.prac.model.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
    List<CartItem> findAllByClientId(Long clientId);
    void deleteAllByClientId(Long clientId);
    CartItem findByClientIdAndProductId(Long clientId, Long productId);
}
