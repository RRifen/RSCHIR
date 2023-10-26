package com.rschir.prac.services;

import com.rschir.prac.model.Book;
import com.rschir.prac.model.Product;
import com.rschir.prac.repositories.ProductsRepository;
import com.rschir.prac.util.enums.ProductType;
import com.rschir.prac.repositories.BooksRepository;
import com.rschir.prac.util.exceptions.ForbiddenException;
import com.rschir.prac.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BooksService {

    private final BooksRepository booksRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, ProductsRepository productsRepository) {
        this.booksRepository = booksRepository;
        this.productsRepository = productsRepository;
    }

    @Transactional(readOnly = true)
    public Book read(long id) {
        return booksRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Book> readAll() {
        return booksRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Book> readAllBySeller(Long sellerNumber) {
        List<Product> products = productsRepository.findAllBySellerNumber(sellerNumber);
        List<Book> result = new ArrayList<>();
        for(Product product : products) {
            Optional<Book> optionalBook = booksRepository.findById(product.getProductId());
            optionalBook.ifPresent(result::add);
        }
        return result;
    }

    @Transactional
    public Book create(Book newBook, Long sellerNumber) {
        newBook.getProduct().setSellerNumber(sellerNumber);
        newBook.getProduct().setProductType(ProductType.BOOK);
        return booksRepository.save(newBook);
    }

    @Transactional
    public Book update(Book updatedBook, long id, Long sellerNumber) {
        Book book = booksRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!sellerNumber.equals(book.getProduct().getSellerNumber())) throw new ForbiddenException();
        updatedBook.setProductId(id);
        updatedBook.getProduct().setProductId(id);
        updatedBook.getProduct().setSellerNumber(sellerNumber);
        updatedBook.getProduct().setProductType(ProductType.ELECTRONIC);

        productsRepository.save(updatedBook.getProduct());
        return booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(long id, Long sellerNumber) {
        Optional<Book> book = booksRepository.findById(id);
        if (book.isPresent() && !book.get().getProduct().getSellerNumber().equals(sellerNumber)) {
            throw new ForbiddenException();
        }
        booksRepository.deleteById(id);
    }
}
