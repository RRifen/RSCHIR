package com.rschir.prac.controllers;

import com.rschir.prac.model.CartItem;
import com.rschir.prac.model.Stats;
import com.rschir.prac.services.CartItemsService;
import com.rschir.prac.util.exceptions.ErrorResponse;
import com.rschir.prac.util.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cart")
@CrossOrigin("*")
public class CartItemsRestController {

    private final CartItemsService cartItemsService;

    @Autowired
    public CartItemsRestController(CartItemsService cartItemsService) {
        this.cartItemsService = cartItemsService;
    }

    @GetMapping
    public List<CartItem> getCartItems() {
        Long clientId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return cartItemsService.findAllByClientId(clientId);
    }

    @GetMapping("/stats")
    public Stats getCartItemsQuantity() {
        Long clientId = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
        return cartItemsService.statsByClientId(clientId);
    }

    @PostMapping
    public List<CartItem> postCartItems(@RequestBody List<CartItem> newCartItems) {
        Long clientId = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
        cartItemsService.addAll(newCartItems, clientId);
        return cartItemsService.findAllByClientId(clientId);
    }

    @DeleteMapping
    public List<CartItem> deleteAllCartItems() {
        Long clientId = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
        return cartItemsService.deleteAllByClientId(clientId);
    }

    @DeleteMapping("/{productId}")
    public void deleteCartItem(@PathVariable long productId) {
        Long clientId = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
        cartItemsService.deleteByClientIdAndProductId(clientId, productId);
    }


    @PatchMapping
    public CartItem updateOneCartItem(@RequestBody CartItem updatedCardItem) {
        Long clientId = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
        return cartItemsService.updateOneCartItem(updatedCardItem, clientId);
    }

    @ExceptionHandler({ForbiddenException.class, AccessDeniedException.class})
    private ResponseEntity<ErrorResponse> handleForbiddenException() {
        ErrorResponse response = new ErrorResponse(
                "You don't have permissions",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
