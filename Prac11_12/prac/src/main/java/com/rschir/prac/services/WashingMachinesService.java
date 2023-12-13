package com.rschir.prac.services;


import com.rschir.prac.model.WashingMachine;
import com.rschir.prac.repositories.WashingMachinesRepository;
import com.rschir.prac.util.analytics.AnalyticsRequest;
import com.rschir.prac.util.enums.ProductType;
import com.rschir.prac.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WashingMachinesService {
    private final WashingMachinesRepository washingMachinesRepository;
    private final AnalyticsRequest analyticsRequest;

    @Autowired
    public WashingMachinesService(WashingMachinesRepository washingMachinesRepository, AnalyticsRequest analyticsRequest) {
        this.washingMachinesRepository = washingMachinesRepository;
        this.analyticsRequest = analyticsRequest;
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
    public WashingMachine create(WashingMachine newWashingMachine) {
        newWashingMachine.setProductType(ProductType.PLUMBING);
        analyticsRequest.callAnalyticsRequest("washing_machine");
        return washingMachinesRepository.save(newWashingMachine);
    }

    @Transactional
    public WashingMachine update(WashingMachine updatedWashingMachine, long id) {
        WashingMachine washingMachine = washingMachinesRepository.findById(id).orElseThrow(NotFoundException::new);
        washingMachine.setCost(updatedWashingMachine.getCost());
        washingMachine.setManufacturer(updatedWashingMachine.getManufacturer());
        washingMachine.setName(updatedWashingMachine.getName());
        washingMachine.setTankVolume(updatedWashingMachine.getTankVolume());
        washingMachine.setSellerNumber(updatedWashingMachine.getSellerNumber());
        return washingMachinesRepository.save(updatedWashingMachine);
    }

    @Transactional
    public void delete(long id) {
        washingMachinesRepository.deleteById(id);
    }
}
