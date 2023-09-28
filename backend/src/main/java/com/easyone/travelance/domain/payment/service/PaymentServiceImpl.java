package com.easyone.travelance.domain.payment.service;

import com.easyone.travelance.domain.member.entity.MainAccount;
import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.respository.MainAccountRepository;
import com.easyone.travelance.domain.member.respository.MemberRepository;
import com.easyone.travelance.domain.payment.dto.*;
import com.easyone.travelance.domain.payment.entity.Calculation;
import com.easyone.travelance.domain.payment.entity.Payment;
import com.easyone.travelance.domain.payment.repository.CalculationRepository;
import com.easyone.travelance.domain.payment.repository.PaymentRepository;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.entity.TravelRoomMember;
import com.easyone.travelance.domain.travel.enumclass.RoomType;
import com.easyone.travelance.domain.travel.repository.TravelRoomMemberRepository;
import com.easyone.travelance.domain.travel.repository.TravelRoomRepository;
import com.easyone.travelance.global.FCM.FirebaseCloudMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MainAccountRepository mainAccountRepository;
    @Autowired
    private TravelRoomRepository travelRoomRepository;
    @Autowired
    private TravelRoomMemberRepository travelRoomMemberRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CalculationRepository calculationRepository;
    @Autowired
    private FirebaseCloudMessageService firebaseCloudMessageService;
    @Autowired
    private WebClient webClient;
    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    @KafkaListener(topics = "travelance", groupId = "travelance")
    public void receivePaymentAlert(PaymentAlertRequestDto paymentAlertRequestDto, Acknowledgment ack) throws IOException {
        try {
            log.info("processPayment 진행 시작");
            processPayment(paymentAlertRequestDto);
            log.info("payment저장 성공");
            // 메시지 처리가 성공적으로 완료된 후 offset을 커밋합니다.
            ack.acknowledge();
        } catch (Exception e) {
            log.info("에러발생");
            throw e; // 현재 상황에서는 예외를 다시 던져 처리하지 않은 메시지로 처리하게 합니다.
        }
    }

    public void processPayment(PaymentAlertRequestDto paymentAlertRequestDto) throws IOException {
        // 1. memberPrivateId를 통해 member 찾기
        Optional<Member> member = memberRepository.findByPrivateId(paymentAlertRequestDto.getMemberPrivateId());
        if (member.isEmpty()){
            throw new EntityNotFoundException("사용자가 존재하지 않습니다.");
        }
        log.info("사용자 찾기 완료");

        // 2. member에 연결된 TravelRoom 중에서 roomType이 NOW인 것 찾기
        TravelRoom currentTravelRoom = member.get().getTravelRoomMember().stream()
                .map(TravelRoomMember::getTravelRoom)
                .filter(room -> room.getIsDone() == RoomType.NOW)
                .findFirst()
                .orElseThrow(()-> new EntityNotFoundException("여행중인 방이 존재하지 않습니다."));
        log.info("여행방 찾기 완료");

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
        log.info("DB 저장 완료");

//        // 5. FCM 알림 전송 (memberId랑 roomNum 같이 전송)
//        Long paymentId = savedPayment.getId();
//        String fcmToken = member.get().getFcmToken();
//        if (fcmToken.isEmpty()){
//            throw new EntityNotFoundException(member.get().getNickname() + "의 FCM TOKEN이 존재하지 않습니다.");
//        } else {
//            String title = "결제 알림";
//            String body = savedPayment.getPaymentContent() + "에서 " + savedPayment.getPaymentAmount() + "원 사용되었습니다.";
//
//            // 변경: 이미 주입된 objectMapper 인스턴스 사용
//            String paymentJson = objectMapper.writeValueAsString(savedPayment);
//
//            firebaseCloudMessageService.sendMessageTo(fcmToken, title, body, paymentJson);
//        }
//        log.info("알림 전송 완료");
    }

    @Override
    public String pushAlertData(PushAlertRequestDto pushAlertRequestDto){
        Optional<Payment> paymentOptional = paymentRepository.findById(pushAlertRequestDto.getPaymentId());
        if (paymentOptional.isEmpty()){
            throw new EntityNotFoundException("결제 내역이 존재하지 않습니다.");
        }

        Payment payment = paymentOptional.get();
        payment.setIsWithPaid(pushAlertRequestDto.isWithPaid());

        paymentRepository.save(payment);
        return "결제내역 저장 성공";
    }


    @Override
    public String completeCalculation(CompleteCalculationRequestDto completeCalculationRequestDto) {
        Optional<TravelRoom> travelRoomOptional = travelRoomRepository.findByIdAndMemberEmail(completeCalculationRequestDto.getRoomNumber(),
                completeCalculationRequestDto.getEmail());
        if (travelRoomOptional.isEmpty()) {
            throw new EntityNotFoundException("여행방을 찾을 수 없습니다.");
        }
        TravelRoom travelRoom = travelRoomOptional.get();
        log.info("여행방 찾기 완료");

        // 1. 최종 공금내역 DB 저장
        for (CompleteCalculationRequestDto.PaymentWith paymentWith : completeCalculationRequestDto.getPaymentWithList()){
            Optional<Payment> paymentOptional = paymentRepository.findById(paymentWith.getPaymentId());

            if (paymentOptional.isEmpty()){
                throw new EntityNotFoundException("Payment Id" + paymentWith.getPaymentId() + "에 해당하는 결제 내역을 찾을 수 없습니다.");
            }
            Payment payment = paymentOptional.get();
            payment.setIsWithPaid(paymentWith.getIsWithPaid());

            paymentRepository.save(payment);
            log.info("공금 내역 저장 완료");
        }

        // 2. 유저의 Travel Room isDone 변경
        TravelRoomMember travelRoomMember = travelRoomMemberRepository.findByTravelRoom_IdAndMember_Email(
                completeCalculationRequestDto.getRoomNumber(),
                completeCalculationRequestDto.getEmail()
        ).orElseThrow(() -> new EntityNotFoundException("여행방 멤버를 찾을 수 없습니다."));

        travelRoomMember.setIsDone(true);
        travelRoomMemberRepository.save(travelRoomMember);
        log.info("isDone 저장 완료");

        List<TravelRoomMember> members = travelRoomMemberRepository.findByTravelRoom(travelRoom);
        // isDone 상태 확인
        boolean allMembersDone = true;
        boolean anyMemberDone = false;
        for (TravelRoomMember member : members) {
            if (member.getMember().getId().equals(completeCalculationRequestDto.getEmail())) {
                member.setIsDone(true);
                travelRoomMemberRepository.save(member); // 상태 변경이 있으므로 저장합니다.
            }
            if (member.isDone()) {
                anyMemberDone = true;
            } else {
                allMembersDone = false;
            }
        }

        // 조건에 따라 TravelRoom의 RoomType을 변경하거나 calculateTransfer 함수를 실행합니다.
        if (allMembersDone) {
            calculateTransfer(travelRoom.getId());
            sendFcmNotificationToAllMembers(travelRoom);
            log.info("함수호출");
        } else if (anyMemberDone) {
            travelRoom.setRoomType(RoomType.WAIT);
            travelRoomRepository.save(travelRoom);
            log.info("RoomType WAIT 변경");
        }
        return null;
    }

    public void calculateTransfer(Long travelRoomId){
        // 1. DB에서 isWithPaid가 true인 Payment를 가져옴
        List<Payment> payments = paymentRepository.findByIsWithPaidAndTravelRoomId(true, travelRoomId);

        Map<Member, Long> memberPayments = new HashMap<>();
        Long totalAmount = 0L;

        // 2. 총 합과 각 개인이 사용한 금액을 구함
        for (Payment payment : payments) {
            totalAmount += payment.getPaymentAmount();
            memberPayments.put(payment.getMember(), memberPayments.getOrDefault(payment.getMember(), 0L) + payment.getPaymentAmount());
        }

        log.warn("총 합계 : " + String.valueOf(totalAmount));
        // 3. TravelRoom의 인원 수로 총 합을 나눔
        Long perPersonAmount = totalAmount/memberPayments.size();
        log.warn("인당 낼 금액 : " + String.valueOf(perPersonAmount));

        // 4. 각 인원의 지출 금액과 1인당 지출 금액을 비교하여 차액을 계산
        for(Map.Entry<Member, Long> entry : memberPayments.entrySet()){
            Member member = entry.getKey();
            Long paidAmount = entry.getValue();
            Long difference = perPersonAmount - paidAmount;

            // 차액이 양수라면 이 회원이 다른 사람에게 돈을 보내야함 (DB저장)
            if (difference > 0){
                for (Map.Entry<Member, Long> innerEntry : memberPayments.entrySet()){
                    if(!member.equals(innerEntry.getKey()) && innerEntry.getValue() - perPersonAmount >0){
                        // 5. 차액을 기반으로 calculation DB에 저장
                        Calculation calculation = Calculation.builder()
                                .fromMemberId(member.getId())
                                .toMemberId(innerEntry.getKey().getId())
                                .amount(difference)
                                .isTransfer(false)
                                .travelRoom(payments.get(0).getTravelRoom())
                                .build();

                        calculationRepository.save(calculation);
                        break;
                    }
                }
            }
        }
    }

    private void sendFcmNotificationToAllMembers(TravelRoom travelRoom){
        List<Member> members = travelRoom.getTravelRoomMembers()
                .stream()
                .map(TravelRoomMember::getMember)
                .collect(Collectors.toList());

        for (Member member : members){
            String fcmToken = member.getFcmToken();
            if (fcmToken==null || fcmToken.isEmpty()){
                throw new EntityNotFoundException("Fcm Token이 존재하지 않습니다.");
            }
            String title = "알림";
            String body = travelRoom.getTravelName() + "의 정산이 완료되었습니다.";

            try {
                firebaseCloudMessageService.sendMessageTo(fcmToken, title, body, null);
            } catch (IOException e) {
                System.out.println("정산 완료 알림 전송 실패");
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String registerCash(RegisterCashRequestDto registerCashRequestDto){
        Optional<Member> existMember = memberRepository.findByEmail(registerCashRequestDto.getEmail());
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
