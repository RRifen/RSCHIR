package com.rschir.prac.services;

import com.rschir.prac.model.Phone;
import com.rschir.prac.repositories.PhonesRepository;
import com.rschir.prac.util.analytics.AnalyticsRequest;
import com.rschir.prac.util.enums.ProductType;
import com.rschir.prac.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PhonesService {

    private final PhonesRepository phonesRepository;
    private final AnalyticsRequest analyticsRequest;

    @Autowired
    public PhonesService(PhonesRepository phonesRepository, AnalyticsRequest analyticsRequest) {
        this.phonesRepository = phonesRepository;
        this.analyticsRequest = analyticsRequest;
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
    public Phone create(Phone newPhone) {
        newPhone.setProductType(ProductType.ELECTRONIC);
        analyticsRequest.callAnalyticsRequest("phone");
        return phonesRepository.save(newPhone);
    }

    @Transactional
    public Phone update(Phone updatedPhone, long id) {
        Phone phone = phonesRepository.findById(id).orElseThrow(NotFoundException::new);
        phone.setCost(updatedPhone.getCost());
        phone.setBatteryCapacity(updatedPhone.getBatteryCapacity());
        phone.setSellerNumber(updatedPhone.getSellerNumber());
        phone.setManufacturer(updatedPhone.getManufacturer());
        phone.setName(updatedPhone.getName());
        return phonesRepository.save(phone);
    }

    @Transactional
    public void delete(long id) {
        phonesRepository.deleteById(id);
    }
}
