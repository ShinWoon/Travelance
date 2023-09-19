package com.easyone.travelance.domain.payment.repository;

import com.easyone.travelance.domain.payment.entity.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculationRepository extends JpaRepository<Calculation, Long> {

}
