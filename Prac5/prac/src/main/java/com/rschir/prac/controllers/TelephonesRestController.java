package com.rschir.prac.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.rschir.prac.model.Telephone;
import com.rschir.prac.services.TelephonesService;
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
@RequestMapping("/telephones")
public class TelephonesRestController {
    TelephonesService telephonesService;

    @Autowired
    public TelephonesRestController(TelephonesService telephonesService) {
        this.telephonesService = telephonesService;
    }

    @GetMapping
    public @ResponseBody List<Telephone> getTelephone() {
        return telephonesService.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Telephone getTelephone(@PathVariable long id) {
        return telephonesService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<Telephone> postTelephone(@RequestBody @JsonView(Views.Post.class) Telephone newTelephone) {
        return new ResponseEntity<>(telephonesService.saveOne(newTelephone), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody Telephone patchTelephone(@PathVariable long id, @RequestBody @JsonView(Views.Post.class) Telephone updatedTelephone) {
        return telephonesService.updateOne(updatedTelephone, id);
    }

    @DeleteMapping("/{id}")
    public void deleteTelephone(@PathVariable long id) {
        telephonesService.deleteOne(id);
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorResponse> handleNotFoundException() {
        ErrorResponse response = new ErrorResponse(
                "Telephone with this id was not found",
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
