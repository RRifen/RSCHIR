package com.rschir.prac.services;

import com.rschir.prac.model.Product;
import com.rschir.prac.model.Phone;
import com.rschir.prac.repositories.ProductsRepository;
import com.rschir.prac.repositories.PhonesRepository;
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
public class PhonesService {

    private final PhonesRepository phonesRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    public PhonesService(PhonesRepository phonesRepository, ProductsRepository productsRepository) {
        this.phonesRepository = phonesRepository;
        this.productsRepository = productsRepository;
    }

    @Transactional(readOnly = true)
    public Phone read(long id) {
        return phonesRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Phone> readAll() {
        return phonesRepository.findAll();
    }

    @Transactional
    public Phone create(Phone newPhone, Long sellerNumber) {
        newPhone.getProduct().setSellerNumber(sellerNumber);
        newPhone.getProduct().setProductType(ProductType.ELECTRONIC);
        return phonesRepository.save(newPhone);
    }

    @Transactional
    public Phone update(Phone updatedPhone, long id, Long sellerNumber) {
        Phone phone = phonesRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!sellerNumber.equals(phone.getProduct().getSellerNumber())) throw new ForbiddenException();
        updatedPhone.setProductId(id);
        updatedPhone.getProduct().setProductId(id);
        updatedPhone.getProduct().setSellerNumber(sellerNumber);
        updatedPhone.getProduct().setProductType(ProductType.ELECTRONIC);

        productsRepository.save(updatedPhone.getProduct());
        return phonesRepository.save(updatedPhone);
    }

    @Transactional
    public void delete(long id, Long sellerNumber) {
        Optional<Phone> telephone = phonesRepository.findById(id);
        if (telephone.isPresent() && !telephone.get().getProduct().getSellerNumber().equals(sellerNumber)) {
            throw new ForbiddenException();
        }
        phonesRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Phone> readAllBySeller(Long sellerNumber) {
        List<Product> products = productsRepository.findAllBySellerNumber(sellerNumber);
        List<Phone> result = new ArrayList<>();
        for(Product product : products) {
            Optional<Phone> optionalTelephone = phonesRepository.findById(product.getProductId());
            optionalTelephone.ifPresent(result::add);
        }
        return result;
    }
}
