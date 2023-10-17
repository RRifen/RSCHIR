package com.rschir.prac.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.rschir.prac.model.Book;
import com.rschir.prac.services.BooksService;
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
@RequestMapping("books")
public class BooksRestController {

    BooksService booksService;

    @Autowired
    public BooksRestController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    @JsonView(Views.Get.class)
    public @ResponseBody List<Book> getBook() {
        return booksService.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Book getBook(@PathVariable long id) {
        return booksService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<Book> postBook(@RequestBody @JsonView(Views.Post.class) Book newBook) {
        return new ResponseEntity<>(booksService.saveOne(newBook), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody Book patchBook(@PathVariable long id, @RequestBody @JsonView(Views.Post.class) Book updatedBook) {
        return booksService.updateOne(updatedBook, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBook(@PathVariable long id) {
        System.out.println(id);
        booksService.deleteOne(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorResponse> handleNotFoundException() {
        ErrorResponse response = new ErrorResponse(
                "Book with this id was not found",
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
