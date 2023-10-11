package com.rschir.prac.repositories;

import com.rschir.prac.model.Telephone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelephonesRepository extends JpaRepository<Telephone, Long> {

}
