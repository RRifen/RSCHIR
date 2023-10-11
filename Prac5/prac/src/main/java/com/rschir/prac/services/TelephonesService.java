package com.rschir.prac.services;

import com.rschir.prac.model.Telephone;
import com.rschir.prac.repositories.TelephonesRepository;
import com.rschir.prac.util.enums.ProductType;
import com.rschir.prac.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TelephonesService {

    private final TelephonesRepository telephonesRepository;

    @Autowired
    public TelephonesService(TelephonesRepository telephonesRepository) {
        this.telephonesRepository = telephonesRepository;
    }

    public Telephone findOne(long id) {
        return telephonesRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Telephone> findAll() {
        return telephonesRepository.findAll();
    }

    @Transactional
    public Telephone saveOne(Telephone newTelephone) {
        newTelephone.setProductType(ProductType.ELECTRONIC);
        return telephonesRepository.save(newTelephone);
    }

    @Transactional
    public Telephone updateOne(Telephone updatedTelephone, long id) {
        Telephone telephone = telephonesRepository.findById(id).orElseThrow(NotFoundException::new);
        telephone.setSellerNumber(updatedTelephone.getSellerNumber());
        telephone.setName(updatedTelephone.getName());
        telephone.setCost(updatedTelephone.getCost());
        telephone.setBatteryCapacity(updatedTelephone.getBatteryCapacity());
        telephone.setManufacturer(updatedTelephone.getManufacturer());
        return telephonesRepository.save(telephone);
    }

    @Transactional
    public void deleteOne(long id) {
        telephonesRepository.deleteById(id);
    }
}
