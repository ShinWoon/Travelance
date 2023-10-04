package com.easyone.travelance.domain.payment.service;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.entity.Profile;
import com.easyone.travelance.domain.member.respository.ProfileRepository;
import com.easyone.travelance.domain.payment.dto.*;
import com.easyone.travelance.domain.payment.entity.Payment;
import com.easyone.travelance.domain.payment.repository.PaymentRepository;
import com.easyone.travelance.domain.travel.dto.NoticeAllResponseDto;
import com.easyone.travelance.domain.travel.dto.RoomUserResponseDto;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.entity.TravelRoomMember;
import com.easyone.travelance.domain.travel.enumclass.RoomType;
import com.easyone.travelance.domain.travel.repository.TravelRoomRepository;
import com.easyone.travelance.domain.travel.service.NoticeService;
import com.easyone.travelance.domain.travel.service.TravelPaymentService;
import com.easyone.travelance.domain.travel.service.TravelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TravelPaymentWithService {
    @Autowired
    private TravelRoomRepository travelRoomRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ProfileRepository profileRepository;

    private final TravelPaymentService travelPaymentService;
    private final NoticeService noticeService;
    private final TravelService travelService;

//    @Cacheable(value = "paymentWiths", key = "#member.id")
    public TravelPaymentPlusDto getPaymentWith(Long roomId, Member member) {

        Optional<TravelRoom> optionalTravelRoom = travelRoomRepository.findById(roomId);
        if (optionalTravelRoom.isEmpty()) {
            throw new NoSuchElementException("No TravelRoom found with id " + roomId);
        }

        TravelRoom travelRoom = optionalTravelRoom.get();
        List<TravelRoomMember> travelRoomMembers = travelRoom.getTravelRoomMembers();

        // 2. 해당 roomId와 memberId를 이용해서 Payment 내역 조회 및 DTO 변환
        List<Payment> paymentsList = paymentRepository.findAllByTravelRoom_IdAndMemberAndIsWithPaidTrue(roomId, member);
        log.info("paymentsList : " + paymentsList);
        List<TravelPaymentResponseDto> travelPaymentResponseDtos = paymentsList.stream()
                .map(TravelPaymentResponseDto::new)
                .collect(Collectors.toList());

        // 3. FriendPayment와 TravelRoomInfo 정보 조회 및 DTO 변환
        List<TravelPaymentPlusDto.FriendPayment> friendPayments = travelRoomMembers.stream()
                .map(travelRoomMember -> {
                    // 해당 여행방과 memberId로 해당 회원의 지불 내역 조회
                    List<Payment> memberPayments = paymentRepository.findAllByTravelRoom_IdAndMemberAndIsWithPaidTrue(roomId, travelRoomMember.getMember());
                    Long totalPayment = memberPayments.stream()
                            .filter(Payment::getIsWithPaid)
                            .mapToLong(Payment::getPaymentAmount)
                            .sum();
                    Profile profile = profileRepository.findByMemberAndTravelRoom(travelRoomMember.getMember(), travelRoom); // profileRepository가 필요합니다.
                    log.info("profile : " + profile);
                    // FriendPayment DTO 생성
                    TravelPaymentPlusDto.FriendPayment friendPayment = new TravelPaymentPlusDto.FriendPayment();
                    friendPayment.setNickName(travelRoomMember.getTravelNickName());
                    friendPayment.setProfileUrl(profile.getProfileUrl());
                    friendPayment.setPaymentAmount(totalPayment);
                    friendPayment.setDone(travelRoomMember.isDone());

                    return friendPayment;
                })
                .collect(Collectors.toList());


        // TravelRoomInfo DTO 생성 및 설정 (필요한 경우)
        TravelPaymentPlusDto.TravelRoomInfo roomInfo = new TravelPaymentPlusDto.TravelRoomInfo();
        roomInfo.setStartDate(travelRoom.getStartDate());
        roomInfo.setEndDate(travelRoom.getEndDate());
        roomInfo.setBudget(travelRoom.getBudget());
        roomInfo.setTravelName(travelRoom.getTravelName());
        // 여기에서 현재 사용자에 해당하는 TravelRoomMember 객체를 찾고, 그 객체의 isDone 값을 가져옵니다.
        Optional<TravelRoomMember> currentUserTravelRoomMember = travelRoomMembers.stream()
                .filter(trm -> trm.getMember().equals(member))
                .findFirst();

        currentUserTravelRoomMember.ifPresent(trm -> roomInfo.setDone(trm.isDone())); // 만약 해당 사용자에 대한 TravelRoomMember 객체가 있으면, 그 객체의 isDone 값을 사용

        List<TravelPaymentPlusDto.TravelRoomInfo> travelRoomInfos = Collections.singletonList(roomInfo);

        // 4. 결과 DTO 생성 및 반환
        TravelPaymentPlusDto result = new TravelPaymentPlusDto();
        result.setTravelPaymentResponseDto(travelPaymentResponseDtos);
        result.setFriendPayments(friendPayments);
        result.setTravelRoomInfo(travelRoomInfos);

        return result;
    }

//    @Cacheable(value = "paymentAlones", key = "#member.id")
    public List<TravelPaymentResponseDto> getPaymentAlone(Long roomId, Member member) {

        // roomId와 memberId를 이용해서 Payment 내역 중 withPaid가 False인 것만 조회
        List<Payment> paymentsList = paymentRepository.findAllByTravelRoom_IdAndMemberAndIsWithPaidFalse(roomId, member);

        return paymentsList.stream().map(TravelPaymentResponseDto::new).collect(Collectors.toList());
    }
//    @Cacheable(value = "travelDoneInfos", key = "#member.id + '-' + #roomId")
    public TravelDoneResponseDto TravelDoneInfo(Member member, Long roomId) {
        TravelRoom travelRoom = travelRoomRepository.findByIdAndMemberId(roomId, member.getId())
                .orElseThrow(()-> new IllegalArgumentException("사용자의 여행방이 없습니다. id =" + roomId));

        //사용한 총 금액
        Long UseTotal = travelPaymentService.TotalPriceTravelId(roomId);

        //1. 여행 이름, 여행 시작 날짜, 여행 끝난 날짜, 여행에서 사용한 총 금액,
        TravelDoneInfoResponseDto travelDoneInfoResponseDto = new TravelDoneInfoResponseDto(travelRoom, UseTotal);

        //2. 해당 여행에서의 전체 공지글들
        List<NoticeAllResponseDto> noticeAllResponseDtoList = noticeService.getAllNotice(roomId);

        //3. 공금 전체 내역
        List<Payment> payments = paymentRepository.findByIsWithPaidAndTravelRoomId(true, roomId);
        List<TravelPaymentResponseDto> AllTravelPaymentResponseDto = payments.stream()
                .map(TravelPaymentResponseDto::new)
                .collect(Collectors.toList());

        //4. 여행 공금을 기준으로 카테고리별로 몇 퍼센트 사용했는지

        Map<String, Long> categoryPaymentsMap = new HashMap<>();

        for (Payment payment: payments) {
            String category = payment.getStoreSector();
            long amount = payment.getPaymentAmount();
            if (categoryPaymentsMap.containsKey(category)) {
                long currentTotal = categoryPaymentsMap.get(category);
                categoryPaymentsMap.put(category, currentTotal + amount);
            } else {
                // 존재하지 않는 카테고리의 경우 0으로 초기화하여 추가
                categoryPaymentsMap.put(category, amount);
            }
        }

        List<CategoryExpenseDto> categoryExpenseDtoList = new ArrayList<>();

        long totalPayment = payments.stream()
                .mapToLong(Payment::getPaymentAmount)
                .sum();

        for (Map.Entry<String, Long> entry : categoryPaymentsMap.entrySet()) {
            String category = entry.getKey();
            long amount = entry.getValue();

            double percent = Math.floor(((double) amount / totalPayment) * 1000.0)/1000.0;
            CategoryExpenseDto categoryExpenseDto = new CategoryExpenseDto(category, percent);

            categoryExpenseDtoList.add(categoryExpenseDto);
        }

        //5. 나의 공금 전체 내역
        List<Payment> paymentsList = paymentRepository.findAllByTravelRoom_IdAndMemberAndIsWithPaidTrue(roomId, member);
        List<TravelPaymentResponseDto> MyTravelPaymentResponseDto = paymentsList.stream()
                .map(TravelPaymentResponseDto::new)
                .collect(Collectors.toList());

        //6. 여행 친구들 조회(RoomUserResponseDto)
        List<RoomUserResponseDto> roomUserResponseDto = travelService.getUserList(roomId);

        return new TravelDoneResponseDto(travelDoneInfoResponseDto,noticeAllResponseDtoList,
                AllTravelPaymentResponseDto, categoryExpenseDtoList, MyTravelPaymentResponseDto, roomUserResponseDto);

    }
}
