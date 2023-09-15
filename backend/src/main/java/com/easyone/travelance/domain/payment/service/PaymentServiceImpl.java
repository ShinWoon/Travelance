package com.easyone.travelance.domain.payment.service;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.respository.MemberRepository;
import com.easyone.travelance.domain.payment.dto.CompleteCalculationRequestDto;
import com.easyone.travelance.domain.payment.dto.PaymentAlertRequestDto;
import com.easyone.travelance.domain.payment.dto.RegisterCashRequestDto;
import com.easyone.travelance.domain.payment.entity.Payment;
import com.easyone.travelance.domain.payment.repository.PaymentRepository;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.enumclass.roomType;
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
        Optional<TravelRoom> existTravelRoom = travelRoomRepository.findByRoomNumber(registerCashRequestDto.getRoomNumber());

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

    @Override
    public String completeCalculation(CompleteCalculationRequestDto completeCalculationRequestDto) {
        Optional<Member> existMember = memberRepository.findById(completeCalculationRequestDto.getMemberId());

        /*
        멤버들이 가진 여행방의 정산상태를 DONE으로 변경
         */
        if (existMember.isEmpty()){
            throw new EntityNotFoundException("사용자가 존재하지 않습니다.");
        }

        Member member = existMember.get();
        List<TravelRoom> travelRoomList = member.getTravelRoomList();

        for (TravelRoom room : travelRoomList){
            if (room.getRoomNumber() == completeCalculationRequestDto.getRoomNumber()){
                room.setIsDone(roomType.DONE);
                travelRoomRepository.save(room);
            }
        }

        /*
        여행방의 isDone 갯수를 파악하여 멤버 수와 일치하면 FCM 알림을 전송
         */
        Optional<TravelRoom> existTravelRoom = travelRoomRepository.findByRoomNumber(completeCalculationRequestDto.getRoomNumber());

        if (existTravelRoom.isEmpty()){
            throw new EntityNotFoundException("여행방이 존재하지 않습니다.");
        }
        TravelRoom travelRoom = existTravelRoom.get();
        Member owner = travelRoom.getMember();

        // 여행방의 모든 Member들의 isDone 상태 체크
        List<TravelRoom> ownerTravelRoom = owner.getTravelRoomList();
        int doneCount = 0;
        for (TravelRoom room : ownerTravelRoom){
            if (room.getIsDone() == roomType.DONE){
                doneCount ++;
            }
        }

        // Member 수와 일치하는지 확인
        if (doneCount == ownerTravelRoom.size()){
            // FCM 알림 전송
            return null;
        }
        return null;
    }
}
