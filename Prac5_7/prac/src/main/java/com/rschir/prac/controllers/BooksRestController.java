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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("books")
@CrossOrigin("*")
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
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    public ResponseEntity<Book> postBook(@RequestBody Book newBook) {
        Long sellerNumber = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
        return new ResponseEntity<>(booksService.create(newBook, sellerNumber), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    public Book patchBook(@PathVariable long id, @RequestBody Book updatedBook) {
        Long sellerNumber = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
        return booksService.update(updatedBook, id, sellerNumber);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteBook(@PathVariable long id) {
        Long sellerNumber = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
        booksService.delete(id, sellerNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/products")
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    public List<Book> getBooksBySeller() {
        Long sellerNumber = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
        return booksService.readAllBySeller(sellerNumber);
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

    @ExceptionHandler({ForbiddenException.class, AccessDeniedException.class})
    private ResponseEntity<ErrorResponse> handleForbiddenException() {
        System.out.println("We are here");
        ErrorResponse response = new ErrorResponse(
                "You don't have permissions",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
