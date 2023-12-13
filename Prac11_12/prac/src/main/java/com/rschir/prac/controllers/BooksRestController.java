package com.rschir.prac.controllers;

import com.rschir.prac.model.Book;
import com.rschir.prac.services.BooksService;
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
@RequestMapping("books")
public class BooksRestController {

    BooksService booksService;

    @Autowired
    public BooksRestController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    public List<Book> getBook() {
        return booksService.readAll();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable long id) {
        return booksService.read(id);
    }


    @PostMapping
    public ResponseEntity<Book> postBook(@RequestBody Book newBook) {
        return new ResponseEntity<>(booksService.create(newBook), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Book patchBook(@PathVariable long id, @RequestBody Book updatedBook) {
        return booksService.update(updatedBook, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable long id) {
        booksService.delete(id);
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
        System.out.println("we are here");
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ForbiddenException.class})
    private ResponseEntity<ErrorResponse> handleForbiddenException() {
        System.out.println("We are here");
        ErrorResponse response = new ErrorResponse(
                "You don't have permissions",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
