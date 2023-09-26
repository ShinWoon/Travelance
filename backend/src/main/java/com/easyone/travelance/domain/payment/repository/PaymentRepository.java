package com.easyone.travelance.domain.payment.repository;

import com.easyone.travelance.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findById(Long paymentId);
    Optional<Payment> findByTravelRoomId(Long roomId);

    List<Payment> findByTravelRoomIdAndMemberId(Long roomId, Long memberId);

    List<Payment> findByIsWithPaidAndTravelRoomId(boolean isPaidWith, Long roomId);
}
