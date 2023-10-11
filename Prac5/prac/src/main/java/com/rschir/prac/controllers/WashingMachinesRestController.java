package com.rschir.prac.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.rschir.prac.model.WashingMachine;
import com.rschir.prac.services.WashingMachinesService;
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
@RequestMapping("/washingMachines")
public class WashingMachinesRestController {
    WashingMachinesService washingMachinesService;

    @Autowired
    public WashingMachinesRestController(WashingMachinesService washingMachinesService) {
        this.washingMachinesService = washingMachinesService;
    }

    @GetMapping
    public @ResponseBody List<WashingMachine> getWashingMachine() {
        return washingMachinesService.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody WashingMachine getWashingMachine(@PathVariable long id) {
        return washingMachinesService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<WashingMachine> postWashingMachine(@RequestBody @JsonView(Views.Post.class) WashingMachine newWashingMachine) {
        return new ResponseEntity<>(washingMachinesService.saveOne(newWashingMachine), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody WashingMachine patchWashingMachine(@PathVariable long id, @RequestBody @JsonView(Views.Post.class) WashingMachine updatedWashingMachine) {
        return washingMachinesService.updateOne(updatedWashingMachine, id);
    }

    @DeleteMapping("/{id}")
    public void deleteWashingMachine(@PathVariable long id) {
        washingMachinesService.deleteOne(id);
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
}
