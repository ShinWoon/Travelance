package com.easyone.travelance.domain.travel.service;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.payment.entity.Payment;
import com.easyone.travelance.domain.payment.repository.PaymentRepository;
import com.easyone.travelance.domain.travel.dto.PaymentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TravelPaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional(readOnly = true)
    public List<PaymentResponseDto> findByTravelId(Member member, Long roomId) {
        return paymentRepository.findByRoomId(roomId).stream()
                .map(payment -> new PaymentResponseDto(payment, member))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PaymentResponseDto> findByTravelIdAndMemberId(Member member, Long roomId) {
        Long memberId = member.getId();
        return paymentRepository.findByRoomIdAndMemberId(roomId, memberId).stream()
                .map(payment -> new PaymentResponseDto(payment, member))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Long TotalPriceTravelId(Long roomId) {
        List<Payment> payments = paymentRepository.findByRoomId(roomId);
        Long totalprice = payments.stream()
                .mapToLong(Payment::getPaymentAmount)
                .sum();
        return totalprice;
    }


}
