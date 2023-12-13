package com.rschir.prac.controllers;

import com.rschir.prac.model.Phone;
import com.rschir.prac.services.PhonesService;
import com.rschir.prac.util.exceptions.ErrorResponse;
import com.rschir.prac.util.exceptions.ForbiddenException;
import com.rschir.prac.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phones")
public class PhonesRestController {
    PhonesService phonesService;

    @Autowired
    public PhonesRestController(PhonesService phonesService) {
        this.phonesService = phonesService;
    }

    @GetMapping
    public List<Phone> getTelephone() {
        return phonesService.readAll();
    }

    @GetMapping("/{id}")
    public Phone getTelephone(@PathVariable long id) {
        return phonesService.read(id);
    }

    @PostMapping
    public ResponseEntity<Phone> postTelephone(@RequestBody Phone newPhone) {
        return new ResponseEntity<>(phonesService.create(newPhone), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Phone patchTelephone(@PathVariable long id, @RequestBody Phone updatedPhone) {
        return phonesService.update(updatedPhone, id);
    }

    @DeleteMapping("/{id}")
    public void deleteTelephone(@PathVariable long id) {
        phonesService.delete(id);
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

    @ExceptionHandler(ForbiddenException.class)
    private ResponseEntity<ErrorResponse> handleForbiddenException() {
        ErrorResponse response = new ErrorResponse(
                "You don't have permissions",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
