package com.easyone.travelance.domain.payment.service;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.respository.MemberRepository;
import com.easyone.travelance.domain.payment.dto.RegisterCashRequestDto;
import com.easyone.travelance.domain.payment.entity.Payment;
import com.easyone.travelance.domain.payment.repository.PaymentRepository;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.repository.TravelRoomRepository;
import com.easyone.travelance.global.FCM.FirebaseCloudMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TravelRoomRepository travelRoomRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private FirebaseCloudMessageService firebaseCloudMessageService;

//    @KafkaListener(topics = "topic name")
//    public void receivePaymentAlert(PaymentAlertRequestDto paymentAlertRequestDto){
//
//    }

    @Override
    public String registerCash(RegisterCashRequestDto registerCashRequestDto){
        Optional<Member> existMember = memberRepository.findById(registerCashRequestDto.getMemberId());
        Optional<TravelRoom> existTravelRoom = travelRoomRepository.findById(registerCashRequestDto.getRoomNumber());

        if (existMember.isEmpty() || existTravelRoom.isEmpty()) {
            throw new EntityNotFoundException("사용자 or 여행방이 존재하지 않습니다.");
        }

        Payment payment = Payment.builder()
                .paymentAt(LocalDateTime.now())
                .paymentAmount(registerCashRequestDto.getPaymentAmount())
                .paymentContent(registerCashRequestDto.getPaymentContent())
                .build();

        paymentRepository.save(payment);

        return "현금 결제내역 저장 성공";
    }



}
