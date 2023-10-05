package com.easyone.travelance.domain.payment.service;

import com.easyone.travelance.domain.member.entity.MainAccount;
import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.entity.Profile;
import com.easyone.travelance.domain.member.respository.MainAccountRepository;
import com.easyone.travelance.domain.member.respository.MemberRepository;
import com.easyone.travelance.domain.member.respository.ProfileRepository;
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
import com.easyone.travelance.domain.travel.service.TravelPaymentService;
import com.easyone.travelance.global.FCM.FCMService;
import com.easyone.travelance.global.FCM.FirebaseCloudMessageService;
import com.easyone.travelance.global.memberInfo.MemberInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.*;
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
    private ProfileRepository profileRepository;
    @Autowired
    private FirebaseCloudMessageService firebaseCloudMessageService;
    @Autowired
    private WebClient webClient;
    @Autowired
    private FCMService fcmService;

    private final TravelPaymentService travelPaymentService;

    public PaymentServiceImpl(TravelPaymentService travelPaymentService) {
        this.travelPaymentService = travelPaymentService;
    }


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

    @Transactional
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

        // 5. FCM 알림 전송
        String fcmToken = member.get().getFcmToken();
        if (fcmToken.isEmpty()){
            throw new EntityNotFoundException(member.get().getNickname() + "의 FCM TOKEN이 존재하지 않습니다.");
        } else {
            String title = "공금에 등록하시겠습니까?";
            String body = savedPayment.getPaymentContent() + "에서 " + savedPayment.getPaymentAmount() + "결제";

//            firebaseCloudMessageService.sendMessageTo(fcmToken, title, body, savedPayment);
            fcmService.registerPaid(fcmToken, savedPayment);

        }
        log.info("알림 전송 완료");
    }

    @Override
    public String pushAlertData(PushAlertRequestDto pushAlertRequestDto){
        Optional<Payment> paymentOptional = paymentRepository.findById(pushAlertRequestDto.getPaymentId());
        if (paymentOptional.isEmpty()){
            throw new EntityNotFoundException("결제 내역이 존재하지 않습니다.");
        }

        Payment payment = paymentOptional.get();
        payment.setIsWithPaid(pushAlertRequestDto.isWithPaid());

        Long memberId = payment.getMember().getId();

        log.info("memberId : " + memberId);

        paymentRepository.save(payment);

//        evictpaymentWithsCache(memberId);
//        evictpaymentAlonesCache(memberId);

        return "결제내역 저장 성공";
    }


    @Override
    public String completeCalculation(MemberInfoDto memberInfoDto,
                                      CompleteCalculationRequestDto completeCalculationRequestDto) {
        Optional<TravelRoom> travelRoomOptional = travelRoomRepository.findByIdAndMemberEmail(completeCalculationRequestDto.getRoomNumber(),
                memberInfoDto.getEmail());
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
                memberInfoDto.getEmail()
        ).orElseThrow(() -> new EntityNotFoundException("여행방 멤버를 찾을 수 없습니다."));

        travelRoomMember.setIsDone(true);
        travelRoomMemberRepository.save(travelRoomMember);
        log.info("isDone 저장 완료");

        List<TravelRoomMember> members = travelRoomMemberRepository.findByTravelRoom(travelRoom);
        // isDone 상태 확인
        boolean allMembersDone = true;
        boolean anyMemberDone = false;
        for (TravelRoomMember member : members) {
            if (member.getMember().getId().equals(memberInfoDto.getEmail())) {
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
            log.info("금액 정산 & FCM 알림 전송");
            calculateTransfer(travelRoom.getId());
            sendFcmNotificationToAllMembers(travelRoom);
            travelRoom.setRoomType(RoomType.WAIT);
            travelRoomRepository.save(travelRoom);
            log.info("RoomType WAIT 변경");
        } else if (anyMemberDone) {
            travelRoom.setRoomType(RoomType.WAIT);
            travelRoomRepository.save(travelRoom);
            log.info("RoomType WAIT 변경");
        }
        return "정산요청 성공";
    }

    public void calculateTransfer(Long travelRoomId) {
        // 1. DB에서 해당 TravelRoom에 대한 공금 Payment를 가져옴
        List<Payment> payments = paymentRepository.findByIsWithPaidAndTravelRoomId(true, travelRoomId);

        // 2. 해당 여행방의 모든 인원을 가져옴
        TravelRoom travelRoom = travelRoomRepository.findById(travelRoomId).orElseThrow(() -> new EntityNotFoundException("TravelRoom을 찾을 수 없습니다."));
        List<TravelRoomMember> allMembers = travelRoom.getTravelRoomMembers();

        // 3. 총 지출과 각 인원별 지출을 계산
        Long totalAmount = 0L;
        Map<Member, Long> memberPayments = new HashMap<>();
        for (Payment payment : payments) {
            totalAmount += payment.getPaymentAmount();
            memberPayments.put(payment.getMember(), memberPayments.getOrDefault(payment.getMember(), 0L) + payment.getPaymentAmount());
        }
        log.warn("총 금액:" + totalAmount);

        // 4. 1인당 지출금액을 계산
        int totalMembers = allMembers.size();
        Long perPersonAmount = totalAmount / totalMembers;
        log.warn("1인당 지출금액" + perPersonAmount);

        // 5. 각 인원별로 지불해야할 금액과 실제 지출한 금액의 차이를 계산하여 이체해야하는 금액을 계산
        for (TravelRoomMember member : allMembers) {
            Long paidAmount = memberPayments.getOrDefault(member.getMember(), 0L); // 수정된 부분
            Long difference = paidAmount - perPersonAmount;
            log.warn("차액" + difference);

            // 이 멤버가 더 많은 돈을 지불했을 경우
            if (difference < 0) {
                for (TravelRoomMember otherMember : allMembers) {
                    if (!member.equals(otherMember)) {
                        Long otherPaidAmount = memberPayments.getOrDefault(otherMember.getMember(), 0L); // 수정된 부분
                        Long otherDifference = otherPaidAmount - perPersonAmount;

                        // 다른 멤버가 더 적은 돈을 지불했을 경우
                        if (otherDifference > 0) {
                            Long transferAmount = Math.min(-difference, otherDifference);
                            log.info("Setting fromMemberId: " + member.getMember().getId());
                            log.info("Setting toMemberId: " + otherMember.getMember().getId());
                            Calculation calculation = Calculation.builder()
                                    .fromMemberId(member.getMember().getId())
                                    .toMemberId(otherMember.getMember().getId())
                                    .amount(transferAmount)
                                    .isTransfer(false)
                                    .travelRoom(travelRoom)
                                    .build();
                            calculationRepository.save(calculation);
                            difference += transferAmount;
                            if (difference >= 0) break;
                        }
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
            fcmService.sendFcmComplete(fcmToken, travelRoom.getId());

//            try {
//                firebaseCloudMessageService.sendMessageTo(fcmToken, title, body, null);
//            } catch (IOException e) {
//                System.out.println("정산 완료 알림 전송 실패");
//                throw new RuntimeException(e);
//            }
        }
    }

    @Override
    public String registerCash(Member member, RegisterCashRequestDto registerCashRequestDto){
        Optional<Member> existMember = memberRepository.findByEmail(member.getEmail());
        Optional<TravelRoom> existTravelRoom = travelRoomRepository.findById(registerCashRequestDto.getRoomNumber());

        if (existMember.isEmpty() || existTravelRoom.isEmpty()) {
            throw new EntityNotFoundException("사용자 or 여행방이 존재하지 않습니다.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime formattedDate = LocalDateTime.now().plusHours(9);
        String formattedDateString = formattedDate.format(formatter);

        Payment payment = Payment.builder()
                .member(existMember.get())
                .travelRoom(existTravelRoom.get())
                .paymentAmount(registerCashRequestDto.getPaymentAmount())
                .paymentContent(registerCashRequestDto.getPaymentContent())
                .paymentAt(formattedDateString)
                .storeSector("그 외")
                .storeAddress("")
                .isWithPaid(true)
                .build();

        paymentRepository.save(payment);
        
        log.info("현금결제 : " + member.getId());

//        evictpaymentAlonesCache(member.getId());
//        evictpaymentWithsCache(member.getId());

        return "현금 결제내역 저장 성공";
    }

    @Override
    // 정산
//    @CacheEvict(value = "roomCacheAll", key = "#member.id")
    public String transferAccount(Member member, TransferAccountRequestDto transferAccountRequestDto) {
        Optional<TravelRoom> existTravelRoom = travelRoomRepository.findById(transferAccountRequestDto.getRoomNumber());

        if (existTravelRoom.isEmpty()){
            throw new EntityNotFoundException("여행방이 존재하지 않습니다.");
        }
        log.info("여행방 찾기완료");

        // 1. 해당 roomNumber로 Calculation을 가져옴
        List<Calculation> calculations = calculationRepository.findByTravelRoomId(existTravelRoom.get().getId());
        log.info("정산 정보 찾기완료");

        // 해당 roomNumber와 fromMemberId가 일치하는 Calculation만 필터링
        List<Calculation> matchedCalculations = calculations.stream()
                .filter(calculation -> calculation.getFromMemberId().equals(member.getId()))
                .collect(Collectors.toList());

        for (Calculation calculation : matchedCalculations) {
            // 2. TransferRequestToBankDto 준비
            TransferRequestToBankDto transferRequestToBankDto = new TransferRequestToBankDto();

            // 3. fromMemberId의 MainAccount로 depositNumber 설정
            Optional<MainAccount> fromAccountOpt = mainAccountRepository.findByMemberId(calculation.getFromMemberId());
            transferRequestToBankDto.setDepositNumber(fromAccountOpt.orElseThrow(() -> new EntityNotFoundException("From Member의 MainAccount를 찾을 수 없습니다.")).getOneAccount());

            // 4. toMemberId의 MainAccount로 withdrawalNumber 설정
            Optional<MainAccount> toAccountOpt = mainAccountRepository.findByMemberId(calculation.getToMemberId());
            transferRequestToBankDto.setWithdrawalNumber(toAccountOpt.orElseThrow(() -> new EntityNotFoundException("To Member의 MainAccount를 찾을 수 없습니다.")).getOneAccount());

            // 5. amount와 memo 설정
            transferRequestToBankDto.setAmount(calculation.getAmount());
            transferRequestToBankDto.setMemo(existTravelRoom.get().getTravelName());
            log.info("계좌이체 요청");

            // 5-1. 로컬 데이트타입
            transferRequestToBankDto.setTransferAt(java.time.LocalDateTime.now());
            log.info("시간까지 옴");
            
            // 6. 계좌 이체 요청
            try {
                ResponseEntity<String> result = webClient.post()
                        .uri("/account/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transferRequestToBankDto)
                        .retrieve()
                        .toEntity(String.class)
                        .block();


                // 이체 성공시 해당 Calculation의 isTransfer와 transferedAt 업데이트
                calculation.setTransfer(true);
                calculation.setTransferedAt(java.time.LocalDateTime.now());
                calculationRepository.save(calculation);

                updateTravelRoomStatus(existTravelRoom.get());
                log.info("방 상태 업데이트");

//                return result.getBody();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return "모든 이체가 완료되었습니다."; // 모든 계산이 완료된 후에 반환될 메시지
    }

    public void updateTravelRoomStatus(TravelRoom travelRoom) {
        // 모든 Calculation의 isTransfer가 true인지 확인합니다.
        boolean allTransferred = calculationRepository.findByTravelRoomId(travelRoom.getId())
                .stream()
                .allMatch(Calculation::isTransfer);

        log.info(String.valueOf(allTransferred));

        if (allTransferred) {
            travelRoom.setRoomType(RoomType.DONE);
            travelRoomRepository.save(travelRoom);
        }
    }

    @Override
    public TransferInfoDto getTransferInfo(Long roomId, Member member){
        TravelRoom travelRoom = travelRoomRepository.findById(roomId).orElseThrow(() -> new EntityNotFoundException("여행방을 찾을 수 없습니다."));
        List<Calculation> calculations = calculationRepository.findByTravelRoomId(roomId);

        // TravelRoomInfo 설정
        TransferInfoDto.TravelRoomInfo roomInfo = new TransferInfoDto.TravelRoomInfo(
                travelRoom.getStartDate(),
                travelRoom.getEndDate(),
                travelRoom.getBudget(),
                travelRoom.getTravelName()
        );

        List<TransferInfoDto.SendInfo> sendInfos = calculations.stream()
                .filter(calc -> calc.getFromMemberId().equals(member.getId()))
                .map(calc -> {
                    Member sendToMember = memberRepository.findById(calc.getToMemberId()).orElse(null);
                    if (sendToMember != null) {
                        Profile sendToProfile = profileRepository.findByMemberAndTravelRoom(sendToMember, travelRoom);
                        return new TransferInfoDto.SendInfo(sendToMember.getNickname(), sendToProfile.getProfileUrl(), calc.getAmount());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<TransferInfoDto.ReceiveInfo> receiveInfos = calculations.stream()
                .filter(calc -> calc.getToMemberId().equals(member.getId()))
                .map(calc -> {
                    Member receiveFromMember = memberRepository.findById(calc.getFromMemberId()).orElse(null);
                    if (receiveFromMember != null) {
                        Profile receiveFromProfile = profileRepository.findByMemberAndTravelRoom(receiveFromMember, travelRoom);
                        return new TransferInfoDto.ReceiveInfo(receiveFromMember.getNickname(), receiveFromProfile.getProfileUrl(), calc.getAmount());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<TravelRoomMember> allMembers = travelRoom.getTravelRoomMembers();
        List<Payment> payments = paymentRepository.findByIsWithPaidAndTravelRoomId(true, roomId);

        // 인원당 금액

        Long totalAmount = 0L;
        for (Payment payment : payments) {
            totalAmount += payment.getPaymentAmount();
        }
        log.warn("총 금액:" + totalAmount);

        Long perAmount = totalAmount / allMembers.size();
        log.warn("1인당 지출금액" + perAmount);

        // 현재 멤버의 금액 (isWithPaid가 true이면서 memberId가 일치하는 값의 합)
        Long myAmount = payments.stream()
                .filter(payment -> payment.getMember().getId().equals(member.getId()))
                .mapToLong(Payment::getPaymentAmount)
                .sum();

        // 보내야하는 금액 or 받아야하는 금액
        Long transferTotalAmount = perAmount-myAmount;

        TransferInfoDto.PaymentInfo paymentInfo = new TransferInfoDto.PaymentInfo();
        paymentInfo.setTotalAmount(totalAmount);
        paymentInfo.setPerAmount(perAmount);
        paymentInfo.setMyAmount(myAmount);
        paymentInfo.setTransferTotalAmount(transferTotalAmount);

        TransferInfoDto transferInfoDto = new TransferInfoDto();
        transferInfoDto.setTravelRoomInfo(roomInfo);
        transferInfoDto.setPaymentInfo(paymentInfo);
        transferInfoDto.setSendInfos(sendInfos);
        transferInfoDto.setReceiveInfos(receiveInfos);

        return transferInfoDto;
    }

//    @CacheEvict(value = "paymentAlones", key = "#memberId")
//    public void evictpaymentAlonesCache(Long memberId) {
//        // 이 메서드는 CacheEvict 어노테이션을 사용하여 캐시를 비우기 위한 용도로만 사용됩니다.
//    }

//    @CacheEvict(value = "paymentWiths", key = "#memberId")
//    public void evictpaymentWithsCache(Long memberId) {
//        // 이 메서드는 CacheEvict 어노테이션을 사용하여 캐시를 비우기 위한 용도로만 사용됩니다.
//    }
}
