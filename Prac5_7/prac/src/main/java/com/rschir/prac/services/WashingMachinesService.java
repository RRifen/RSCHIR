package com.rschir.prac.services;


import com.rschir.prac.model.Product;
import com.rschir.prac.model.WashingMachine;
import com.rschir.prac.repositories.ProductsRepository;
import com.rschir.prac.repositories.WashingMachinesRepository;
import com.rschir.prac.util.enums.ProductType;
import com.rschir.prac.util.exceptions.ForbiddenException;
import com.rschir.prac.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WashingMachinesService {
    private final WashingMachinesRepository washingMachinesRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    public WashingMachinesService(WashingMachinesRepository washingMachinesRepository, ProductsRepository productsRepository) {
        this.washingMachinesRepository = washingMachinesRepository;
        this.productsRepository = productsRepository;
    }

    @Transactional(readOnly = true)
    public WashingMachine read(long id) {
        return washingMachinesRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<WashingMachine> readAll() {
        return washingMachinesRepository.findAll();
    }

    @Transactional
    public WashingMachine create(WashingMachine newWashingMachine, Long sellerNumber) {
        newWashingMachine.getProduct().setSellerNumber(sellerNumber);
        newWashingMachine.getProduct().setProductType(ProductType.PLUMBING);
        return washingMachinesRepository.save(newWashingMachine);
    }

    @Transactional
    public WashingMachine update(WashingMachine updatedWashingMachine, long id, Long sellerNumber) {
        WashingMachine washingMachine = washingMachinesRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!sellerNumber.equals(washingMachine.getProduct().getSellerNumber())) throw new ForbiddenException();
        updatedWashingMachine.setProductId(id);
        updatedWashingMachine.getProduct().setProductId(id);
        updatedWashingMachine.getProduct().setSellerNumber(sellerNumber);
        updatedWashingMachine.getProduct().setProductType(ProductType.ELECTRONIC);

        productsRepository.save(updatedWashingMachine.getProduct());
        return washingMachinesRepository.save(updatedWashingMachine);
    }

    @Transactional
    public void delete(long id, Long sellerNumber) {
        Optional<WashingMachine> washingMachine = washingMachinesRepository.findById(id);
        if (washingMachine.isPresent() && !washingMachine.get().getProduct().getSellerNumber().equals(sellerNumber)) {
            throw new ForbiddenException();
        }
        washingMachinesRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<WashingMachine> readAllBySeller(Long sellerNumber) {
        List<Product> products = productsRepository.findAllBySellerNumber(sellerNumber);
        List<WashingMachine> result = new ArrayList<>();
        for(Product product : products) {
            Optional<WashingMachine> optionalWashingMachine = washingMachinesRepository.findById(product.getProductId());
            optionalWashingMachine.ifPresent(result::add);
        }
        return result;
    }
}
