package com.rschir.prac.repositories;

import com.rschir.prac.model.WashingMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WashingMachinesRepository extends JpaRepository<WashingMachine, Long> {

}
