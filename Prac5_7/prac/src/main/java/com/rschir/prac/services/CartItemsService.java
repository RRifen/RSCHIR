package com.rschir.prac.services;

import com.rschir.prac.model.CartItem;
import com.rschir.prac.model.CartItemId;
import com.rschir.prac.model.Product;
import com.rschir.prac.model.Stats;
import com.rschir.prac.repositories.CartItemRepository;
import com.rschir.prac.repositories.ProductsRepository;
import com.rschir.prac.util.exceptions.NotFoundException;
import com.rschir.prac.util.exceptions.ProductAlreadyUsedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartItemsService {
    private final CartItemRepository cartItemRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    public CartItemsService(CartItemRepository cartItemRepository, ProductsRepository productsRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productsRepository = productsRepository;
    }

    public List<CartItem> findAllByClientId(Long clientId) {
        return cartItemRepository.findAllByClientId(clientId);
    }

    @Transactional
    public void addAll(List<CartItem> newCartItems, Long clientId) {
        for(var cartItem : newCartItems) {
            cartItem.setClientId(clientId);
        }
        cartItemRepository.saveAll(newCartItems);
    }

    @Transactional
    public List<CartItem> deleteAllByClientId(long clientId) {
        cartItemRepository.deleteAllByClientId(clientId);
        return cartItemRepository.findAllByClientId(clientId);
    }

    @Transactional
    public CartItem updateOneCartItem(CartItem updatedCardItem, Long clientId) {
        System.out.println(updatedCardItem.getProductId());
        CartItem toUpdate = cartItemRepository.findByClientIdAndProductId(clientId, updatedCardItem.getProductId());
        toUpdate.setQuantity(updatedCardItem.getQuantity());
        return cartItemRepository.save(toUpdate);
    }


    @Transactional
    public void deleteByClientIdAndProductId(long clientId, long productId) {
        cartItemRepository.deleteById(new CartItemId(clientId, productId));
    }

    @Transactional
    public void makeOrder(long clientId) {
        List<CartItem> cartItems = cartItemRepository.findAllByClientId(clientId);
        if (cartItems.isEmpty()) {
            return;
        }
        for(CartItem cartItem : cartItems) {
            if (cartItem.getQuantity() > cartItem.getProduct().getCount()) {
                throw new ProductAlreadyUsedException();
            }
        }
        for(CartItem cartItem : cartItems) {
            Product product = productsRepository.findById(cartItem.getProductId()).orElseThrow(NotFoundException::new);
            product.setCount(product.getCount() - cartItem.getQuantity());
            productsRepository.save(product);
            cartItemRepository.deleteById(new CartItemId(cartItem.getClientId(), cartItem.getProductId()));
        }
    }

    public Stats statsByClientId(Long clientId) {
        int quantity = 0;
        double totalCost = 0;
        for(var cartItem : cartItemRepository.findAllByClientId(clientId)) {
            quantity += cartItem.getQuantity();
            totalCost += cartItem.getProduct().getCost() * cartItem.getQuantity();
        }
        return new Stats(quantity, totalCost);
    }
}
