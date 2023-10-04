package com.easyone.travelance.domain.payment.repository;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findById(Long paymentId);
    List<Payment> findByTravelRoomId(Long roomId);
    List<Payment> findByTravelRoomIdAndMemberId(Long roomId, Long memberId);

    List<Payment> findByIsWithPaidAndTravelRoomId(boolean isPaidWith, Long roomId);
    List<Payment> findAllByTravelRoom_IdAndMemberAndIsWithPaidTrue(Long roomId, Member member);
    List<Payment> findAllByTravelRoom_IdAndMemberAndIsWithPaidFalse(Long roomId, Member member);
    List<Payment> findByTravelRoomIdAndStoreAddressAndIsWithPaidIsTrue(Long roomId, String StoreAddress);
    List<Payment> findByTravelRoomIdAndIsWithPaidIsTrue(Long roomId);



}
