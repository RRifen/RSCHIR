package com.rschir.prac.services;

import com.rschir.prac.model.Book;
import com.rschir.prac.repositories.BooksRepository;
import com.rschir.prac.util.analytics.AnalyticsRequest;
import com.rschir.prac.util.enums.ProductType;
import com.rschir.prac.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BooksService {

    private final BooksRepository booksRepository;
    private final AnalyticsRequest analyticsRequest;

    @Autowired
    public BooksService(BooksRepository booksRepository, AnalyticsRequest analyticsRequest) {
        this.booksRepository = booksRepository;
        this.analyticsRequest = analyticsRequest;
    }

    @Transactional(readOnly = true)
    public Book read(long id) {
        return booksRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Book> readAll() {
        return booksRepository.findAll();
    }

    @Transactional
    public Book create(Book newBook) {
        newBook.setProductType(ProductType.BOOK);

        analyticsRequest.callAnalyticsRequest("book");
        return booksRepository.save(newBook);
    }

    @Transactional
    public Book update(Book updatedBook, long id) {
        Book book = booksRepository.findById(id).orElseThrow(NotFoundException::new);
        book.setSellerNumber(updatedBook.getSellerNumber());
        book.setAuthor(updatedBook.getAuthor());
        book.setCost(updatedBook.getCost());
        book.setName(updatedBook.getName());
        book.setProductType(ProductType.BOOK);
        return booksRepository.save(book);
    }

    @Transactional
    public void delete(long id) {
        booksRepository.deleteById(id);
    }
}
