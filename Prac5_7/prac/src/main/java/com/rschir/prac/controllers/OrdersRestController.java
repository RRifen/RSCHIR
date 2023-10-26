package com.rschir.prac.controllers;

import com.rschir.prac.services.CartItemsService;
import com.rschir.prac.util.exceptions.ErrorResponse;
import com.rschir.prac.util.exceptions.ProductAlreadyUsedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("order")
@CrossOrigin("*")
public class OrdersRestController {

    private final CartItemsService cartItemsService;

    public OrdersRestController(CartItemsService cartItemsService) {
        this.cartItemsService = cartItemsService;
    }

    @PostMapping("/make")
    public void makeOrder() {
        Long clientId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
        cartItemsService.makeOrder(clientId);
    }

    @ExceptionHandler(ProductAlreadyUsedException.class)
    private ResponseEntity<ErrorResponse> handleForbiddenException() {
        ErrorResponse response = new ErrorResponse(
                "Some products are out of stock",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
