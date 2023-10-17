package com.rschir.prac.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.rschir.prac.model.Client;
import com.rschir.prac.util.views.Views;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
public class CartRestController {
    @GetMapping("/{id}")
    public @ResponseBody Cart getCart(@PathVariable long id) {
        return cartService.findOne(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Client> postProductToCart(@RequestBody @JsonView(Views.Post.class) Client newClient) {
        return new ResponseEntity<>(clientsService.saveOne(newClient), HttpStatus.CREATED);
    }
}
