package com.rschir.prac.services;


import com.rschir.prac.model.WashingMachine;
import com.rschir.prac.repositories.WashingMachinesRepository;
import com.rschir.prac.util.enums.ProductType;
import com.rschir.prac.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class WashingMachinesService {
    private final WashingMachinesRepository washingMachinesRepository;

    @Autowired
    public WashingMachinesService(WashingMachinesRepository washingMachinesRepository) {
        this.washingMachinesRepository = washingMachinesRepository;
    }

    public WashingMachine findOne(long id) {
        return washingMachinesRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<WashingMachine> findAll() {
        return washingMachinesRepository.findAll();
    }

    @Transactional
    public WashingMachine saveOne(WashingMachine newWashingMachine) {
        newWashingMachine.setProductType(ProductType.PLUMBING);
        return washingMachinesRepository.save(newWashingMachine);
    }

    @Transactional
    public WashingMachine updateOne(WashingMachine updatedWashingMachine, long id) {
        WashingMachine washingMachine = washingMachinesRepository.findById(id).orElseThrow(NotFoundException::new);
        washingMachine.setSellerNumber(updatedWashingMachine.getSellerNumber());
        washingMachine.setManufacturer(updatedWashingMachine.getManufacturer());
        washingMachine.setTankVolume(updatedWashingMachine.getTankVolume());
        washingMachine.setName(updatedWashingMachine.getName());
        washingMachine.setCost(updatedWashingMachine.getCost());
        return washingMachinesRepository.save(washingMachine);
    }

    @Transactional
    public void deleteOne(long id) {
        washingMachinesRepository.deleteById(id);
    }
}
