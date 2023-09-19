package com.easyone.travelance.domain.payment.service;

import com.easyone.travelance.domain.member.entity.MainAccount;
import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.respository.MainAccountRepository;
import com.easyone.travelance.domain.member.respository.MemberRepository;
import com.easyone.travelance.domain.payment.dto.*;
import com.easyone.travelance.domain.payment.entity.Payment;
import com.easyone.travelance.domain.payment.repository.CalculationRepository;
import com.easyone.travelance.domain.payment.repository.PaymentRepository;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.entity.TravelRoomMember;
import com.easyone.travelance.domain.travel.enumclass.RoomType;
import com.easyone.travelance.domain.travel.repository.TravelRoomRepository;
import com.easyone.travelance.global.FCM.FirebaseCloudMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MainAccountRepository mainAccountRepository;
    @Autowired
    private TravelRoomRepository travelRoomRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CalculationRepository calculationRepository;
    @Autowired
    private FirebaseCloudMessageService firebaseCloudMessageService;
    @Autowired
    private WebClient webClient;

    @KafkaListener(topics = "topic name")
    public void receivePaymentAlert(PaymentAlertRequestDto paymentAlertRequestDto){
        // 1. memberPrivateId를 통해 member 찾기
        Optional<Member> member = memberRepository.findByPrivateId(paymentAlertRequestDto.getMemberPrivateId());
        if (member.isEmpty()){
            throw new EntityNotFoundException("사용자가 존재하지 않습니다.");
        }

        // 2. member에 연결된 TravelRoom 중에서 roomType이 NOW인 것 찾기
        TravelRoom currentTravelRoom = member.get().getTravelRoomMember().stream()
                .map(TravelRoomMember::getTravelRoom)
                .filter(room -> room.getIsDone() == RoomType.NOW)
                .findFirst()
                .orElseThrow(()-> new EntityNotFoundException("여행중인 방이 존재하지 않습니다."));

        // 3. DTO에서 받은 정보를 payment DB에 저장
        Payment payment = Payment.builder()
                .member(member.get())
                .travelRoom(currentTravelRoom)
                .paymentAt(paymentAlertRequestDto.getPaymentAt())
                .paymentAmount(paymentAlertRequestDto.getPaymentAmount())
                .paymentContent(paymentAlertRequestDto.getPaymentContent())
                .storeAddress(paymentAlertRequestDto.getStoreAddress())
                .storeSector(paymentAlertRequestDto.getStoreSector())
                .isWithPaid(false)
                .build();

        // 4. DB 저장
        Payment savedPayment = paymentRepository.save(payment);

        // 5. FCM 알림 전송 (memberId랑 roomNum 같이 전송)
        /*
        ##########################################################################
        Long paymentId = savedPayment.getId();
        firebaseCloudMessageService.sendToMessage()
         */

    }

    @Override
    public String pushAlertData(PushAlertRequestDto pushAlertRequestDto){
        Optional<Payment> paymentOptional = paymentRepository.findById(pushAlertRequestDto.getPaymentId());
        if (paymentOptional.isEmpty()){
            throw new EntityNotFoundException("결제 내역이 존재하지 않습니다.");
        }

        Payment payment = paymentOptional.get();
        payment.setWithPaid(pushAlertRequestDto.isWithPaid());

        paymentRepository.save(payment);
        return "Payment updated successfully";
    }

    @Override
    public String registerCash(RegisterCashRequestDto registerCashRequestDto){
        Optional<Member> existMember = memberRepository.findById(registerCashRequestDto.getMemberId());
        Optional<TravelRoom> existTravelRoom = travelRoomRepository.findById(registerCashRequestDto.getRoomNumber());

        if (existMember.isEmpty() || existTravelRoom.isEmpty()) {
            throw new EntityNotFoundException("사용자 or 여행방이 존재하지 않습니다.");
        }

        Payment payment = Payment.builder()
                .paymentAmount(registerCashRequestDto.getPaymentAmount())
                .paymentContent(registerCashRequestDto.getPaymentContent())
                .build();

        paymentRepository.save(payment);

        return "현금 결제내역 저장 성공";
    }

    @Override
    public String completeCalculation(CompleteCalculationRequestDto completeCalculationRequestDto) {
        // 1. 최종 공금내역 DB 저장

        // 2. 유저의 Travel RoomType 변경
        Optional<TravelRoom> travelRoomOptional = travelRoomRepository.findByIdAndMemberId(completeCalculationRequestDto.getRoomNumber(),
                completeCalculationRequestDto.getMemberId());

        if (travelRoomOptional.isEmpty()) {
            throw new EntityNotFoundException("여행방을 찾을 수 없습니다.");
        }

        TravelRoom travelRoom = travelRoomOptional.get();
        travelRoom.setRoomType(RoomType.WAIT);
        travelRoomRepository.save(travelRoom);

        // roomType 확인
        if (!areAllMembersInwaitState(travelRoom)){
            throw new IllegalStateException("정산 진행 중입니다.");
        }else{
            // 정산 로직 추가
            // 정산완료 FCM 알림 추가
        }

        return null;
    }

    private boolean areAllMembersInwaitState(TravelRoom travelRoom){
        return travelRoom.getTravelRoomMembers()
                .stream()
                .allMatch(travelRoomMember -> travelRoomMember.getTravelRoom().getIsDone()==RoomType.WAIT);
    }

    @Override
    public String transferAccount(TransferAccountRequestDto transferAccountRequestDto) {
        Optional<TravelRoom> existTravelRoom = travelRoomRepository.findById(transferAccountRequestDto.getRoomNumber());
        if (existTravelRoom.isEmpty()){
            throw new EntityNotFoundException("여행방이 존재하지 않습니다.");
        }
        Optional<MainAccount> fromAccountOpt = mainAccountRepository.findByMemberId(transferAccountRequestDto.getFromMemberId());
        String depositNumber = fromAccountOpt.isPresent() ? fromAccountOpt.get().getOneAccount() : null;

        Optional<MainAccount> toAccountOpt = mainAccountRepository.findByMemberId(transferAccountRequestDto.getToMemberId());
        String withdrawalNumber = toAccountOpt.isPresent() ? toAccountOpt.get().getOneAccount() : null;

        TransferRequestToBankDto transferRequestToBankDto = new TransferRequestToBankDto();
        transferRequestToBankDto.setDepositNumber(depositNumber);
        transferRequestToBankDto.setWithdrawalNumber(withdrawalNumber);
        transferRequestToBankDto.setAmount(transferAccountRequestDto.getAmount());
        transferRequestToBankDto.setMemo(existTravelRoom.get().getTravelName());

        try {
            ResponseEntity<String> result = webClient.post()
                    .uri("/account/transfer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(transferRequestToBankDto)
                    .retrieve()
                    .toEntity(String.class)
                    .block();
            return result.getBody();

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
