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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phones")
@CrossOrigin("*")
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

    @GetMapping("/products")
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    public List<Phone> getBooksBySeller() {
        Long sellerNumber = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
        return phonesService.readAllBySeller(sellerNumber);
    }

    @PostMapping
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    public ResponseEntity<Phone> postTelephone(@RequestBody Phone newPhone) {
        Long sellerNumber = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return new ResponseEntity<>(phonesService.create(newPhone, sellerNumber), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    public Phone patchTelephone(@PathVariable long id, @RequestBody Phone updatedPhone) {
        Long sellerNumber = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
        return phonesService.update(updatedPhone, id, sellerNumber);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    public void deleteTelephone(@PathVariable long id) {
        Long sellerNumber = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
        phonesService.delete(id, sellerNumber);
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
