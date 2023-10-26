package com.rschir.prac.controllers;

import com.rschir.prac.model.WashingMachine;
import com.rschir.prac.services.WashingMachinesService;
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
@RequestMapping("/washing_machines")
@CrossOrigin("*")
public class WashingMachinesRestController {
    WashingMachinesService washingMachinesService;

    @Autowired
    public WashingMachinesRestController(WashingMachinesService washingMachinesService) {
        this.washingMachinesService = washingMachinesService;
    }

    @GetMapping
    public List<WashingMachine> getWashingMachine() {
        return washingMachinesService.readAll();
    }

    @GetMapping("/{id}")
    public WashingMachine getWashingMachine(@PathVariable long id) {
        return washingMachinesService.read(id);
    }

    @GetMapping("/products")
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    public List<WashingMachine> getBooksBySeller() {
        Long sellerNumber = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
        return washingMachinesService.readAllBySeller(sellerNumber);
    }

    @PostMapping
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    public ResponseEntity<WashingMachine> postWashingMachine(@RequestBody WashingMachine newWashingMachine) {
        Long sellerNumber = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return new ResponseEntity<>(washingMachinesService.create(newWashingMachine, sellerNumber), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    public WashingMachine patchWashingMachine(@PathVariable long id, @RequestBody WashingMachine updatedWashingMachine) {
        Long sellerNumber = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
        return washingMachinesService.update(updatedWashingMachine, id, sellerNumber);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    public void deleteWashingMachine(@PathVariable long id) {
        Long sellerNumber = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
        washingMachinesService.delete(id, sellerNumber);
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorResponse> handleNotFoundException() {
        ErrorResponse response = new ErrorResponse(
                "WashingMachine with this id was not found",
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
