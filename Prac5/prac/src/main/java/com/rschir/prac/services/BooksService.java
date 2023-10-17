package com.rschir.prac.services;

import com.rschir.prac.model.Book;
import com.rschir.prac.model.Product;
import com.rschir.prac.util.enums.ProductType;
import com.rschir.prac.repositories.BooksRepository;
import com.rschir.prac.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public Book findOne(long id) {
        return booksRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    @Transactional
    public Book saveOne(Book newBook) {
        newBook.getProduct().setProductType(ProductType.BOOK);
        return booksRepository.save(newBook);
    }

    @Transactional
    public Book updateOne(Book updatedBook, long id) {
        Book book = booksRepository.findById(id).orElseThrow(NotFoundException::new);
        Product bookProduct = book.getProduct();
        Product updatedBookProduct = updatedBook.getProduct();

        bookProduct.setSellerNumber(updatedBook.getProduct().getSellerNumber());
        book.setAuthor(updatedBook.getAuthor());
        bookProduct.setName(updatedBookProduct.getName());
        bookProduct.setCost(updatedBookProduct.getCost());
        return booksRepository.save(book);
    }

    @Transactional
    public void deleteOne(long id) {
        booksRepository.deleteById(id);
    }
}
