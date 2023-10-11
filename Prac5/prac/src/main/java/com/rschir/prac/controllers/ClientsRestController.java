package com.rschir.prac.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.rschir.prac.model.Client;
import com.rschir.prac.services.ClientsService;
import com.rschir.prac.util.exceptions.ErrorResponse;
import com.rschir.prac.util.exceptions.NotFoundException;
import com.rschir.prac.util.views.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientsRestController {
    ClientsService clientsService;

    @Autowired
    public ClientsRestController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping
    public @ResponseBody List<Client> getClient() {
        return clientsService.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Client getClient(@PathVariable long id) {
        return clientsService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<Client> postClient(@RequestBody @JsonView(Views.Post.class) Client newClient) {
        return new ResponseEntity<>(clientsService.saveOne(newClient), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody Client patchClient(@PathVariable long id, @RequestBody @JsonView(Views.Post.class) Client updatedClient) {
        return clientsService.updateOne(updatedClient, id);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable long id) {
        clientsService.deleteOne(id);
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorResponse> handleNotFoundException() {
        ErrorResponse response = new ErrorResponse(
                "Client with this id was not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, DataIntegrityViolationException.class})
    private ResponseEntity<ErrorResponse> handleBadParseException(Exception e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
