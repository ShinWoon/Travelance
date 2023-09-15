package com.easyone.travelance.domain.payment.repository;

import com.easyone.travelance.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
