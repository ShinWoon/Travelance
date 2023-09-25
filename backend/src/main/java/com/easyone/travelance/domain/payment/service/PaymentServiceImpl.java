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
import com.easyone.travelance.domain.travel.repository.TravelRoomRepository;
import com.easyone.travelance.global.FCM.FirebaseCloudMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

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

    @KafkaListener(topics = "travelance")
    public void receivePaymentAlert(PaymentAlertRequestDto paymentAlertRequestDto) throws IOException {
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
        Long paymentId = savedPayment.getId();
        String fcmToken = member.get().getFcmToken();
        if (fcmToken.isEmpty()){
            throw new EntityNotFoundException(member.get().getNickname() + "의 FCM TOKEN이 존재하지 않습니다.");
        } else {
            String title = "결제 알림";
            String body = savedPayment.getPaymentContent() + "에서 " + savedPayment.getPaymentAmount() + "원 사용되었습니다.";

            ObjectMapper objectMapper = new ObjectMapper();
            String paymentJson = objectMapper.writeValueAsString(savedPayment);

            firebaseCloudMessageService.sendMessageTo(fcmToken, title, body, paymentJson);
        }
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
        return "Payment updated successfully";
    }


    @Override
    public String completeCalculation(CompleteCalculationRequestDto completeCalculationRequestDto) {
        // 1. 최종 공금내역 DB 저장
        for (CompleteCalculationRequestDto.PaymentWith paymentWith : completeCalculationRequestDto.getPaymentWithList()){
            Optional<Payment> paymentOptional = paymentRepository.findById(paymentWith.getPaymentId());

            if (paymentOptional.isEmpty()){
                throw new EntityNotFoundException("Payment Id" + paymentWith.getPaymentId() + "에 해당하는 결제 내역을 찾을 수 없습니다.");
            }
            Payment payment = paymentOptional.get();
            payment.setIsWithPaid(paymentWith.getIsWithPaid());

            paymentRepository.save(payment);
        }

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
            // 정산 로직
            calculateTransfer(travelRoom.getId());
            // 정산완료 FCM 알림 추가
            sendFcmNotificationToAllMembers(travelRoom);
        }

        return null;
    }

    private boolean areAllMembersInwaitState(TravelRoom travelRoom){
        return travelRoom.getTravelRoomMembers()
                .stream()
                .allMatch(travelRoomMember -> travelRoomMember.getTravelRoom().getIsDone()==RoomType.WAIT);
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

        // 3. TravelRoom의 인원 수로 총 합을 나눔
        Long perPersonAmount = totalAmount/memberPayments.size();

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
