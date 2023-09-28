package com.easyone.travelance.domain.payment.service;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.entity.Profile;
import com.easyone.travelance.domain.member.respository.ProfileRepository;
import com.easyone.travelance.domain.payment.dto.TravelPaymentPlusDto;
import com.easyone.travelance.domain.payment.dto.TravelPaymentResponseDto;
import com.easyone.travelance.domain.payment.entity.Payment;
import com.easyone.travelance.domain.payment.repository.PaymentRepository;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.entity.TravelRoomMember;
import com.easyone.travelance.domain.travel.enumclass.RoomType;
import com.easyone.travelance.domain.travel.repository.TravelRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelPaymentWithService {
    @Autowired
    private TravelRoomRepository travelRoomRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public TravelPaymentPlusDto getPaymentWith(Member member) {
        // 1. 현재 회원이 속한 여행방 중에서 RoomType이 NOW인 것을 조회
        List<TravelRoom> travelRooms = travelRoomRepository.findAllByTravelRoomMembers_MemberAndIsDone(member, RoomType.NOW);

        if (travelRooms.isEmpty()) {
            throw new EntityNotFoundException("해당 사용자의 NOW 상태의 여행방이 없습니다.");
        }

        // 첫 번째 NOW 상태의 여행방의 ID를 가져옵니다.
        TravelRoom travelRoom = travelRooms.get(0);
        Long roomId = travelRooms.get(0).getId();
        List<TravelRoomMember> travelRoomMembers = travelRoom.getTravelRoomMembers();

        // 2. 해당 roomId와 memberId를 이용해서 Payment 내역 조회 및 DTO 변환
        List<Payment> paymentsList = paymentRepository.findAllByTravelRoom_IdAndMemberAndIsWithPaidTrue(roomId, member);
        List<TravelPaymentResponseDto> travelPaymentResponseDtos = paymentsList.stream()
                .map(TravelPaymentResponseDto::new)
                .collect(Collectors.toList());

        // 3. FriendPayment와 TravelRoomInfo 정보 조회 및 DTO 변환
        List<TravelPaymentPlusDto.FriendPayment> friendPayments = travelRoomMembers.stream()
                .map(travelRoomMember -> {
                    // 해당 여행방과 memberId로 해당 회원의 지불 내역 조회
                    List<Payment> memberPayments = paymentRepository.findAllByTravelRoom_IdAndMemberAndIsWithPaidTrue(roomId, travelRoomMember.getMember());

                    // 결제 내역 합계 계산 (isWithPaid가 true인 것만 합산)
                    Long totalPayment = memberPayments.stream()
                            .filter(Payment::getIsWithPaid)
                            .mapToLong(Payment::getPaymentAmount)
                            .sum();
                    Profile profile = profileRepository.findByMemberAndTravelRoom(travelRoomMember.getMember(), travelRoom); // profileRepository가 필요합니다.

                    // FriendPayment DTO 생성
                    TravelPaymentPlusDto.FriendPayment friendPayment = new TravelPaymentPlusDto.FriendPayment();
                    friendPayment.setNickName(travelRoomMember.getTravelNickName());
                    friendPayment.setProfileUrl(profile.getProfileUrl());
                    friendPayment.setPaymentAmount(totalPayment);
                    friendPayment.setIsDone(travelRoomMember.isDone());

                    return friendPayment;
                })
                .collect(Collectors.toList());


        // TravelRoomInfo DTO 생성 및 설정 (필요한 경우)
        TravelPaymentPlusDto.TravelRoomInfo roomInfo = new TravelPaymentPlusDto.TravelRoomInfo();
        roomInfo.setStartDate(travelRoom.getStartDate());
        roomInfo.setEndDate(travelRoom.getEndDate());
        roomInfo.setBudget(travelRoom.getBudget());

        List<TravelPaymentPlusDto.TravelRoomInfo> travelRoomInfos = Collections.singletonList(roomInfo);

        // 4. 결과 DTO 생성 및 반환
        TravelPaymentPlusDto result = new TravelPaymentPlusDto();
        result.setTravelPaymentResponseDto(travelPaymentResponseDtos);
        result.setFriendPayments(friendPayments);
        result.setTravelRoomInfo(travelRoomInfos);

        return result;
    }

    public List<TravelPaymentResponseDto> getPaymentAlone(Member member) {

        // 1. 현재 회원이 속한 여행방 중에서 RoomType이 NOW인 것을 조회
        List<TravelRoom> travelRooms = travelRoomRepository.findAllByTravelRoomMembers_MemberAndIsDone(member, RoomType.NOW);

        if (travelRooms.isEmpty()) {
            throw new EntityNotFoundException("해당 사용자의 NOW 상태의 여행방이 없습니다.");
        }

        // 첫 번째 NOW 상태의 여행방의 ID를 가져옵니다.
        // (멤버가 여러 NOW 상태의 여행방에 속할 경우, 추가적인 로직 필요)
        Long roomId = travelRooms.get(0).getId();

        // 2. roomId와 memberId를 이용해서 Payment 내역 중 withPaid가 False인 것만 조회
        List<Payment> paymentsList = paymentRepository.findAllByTravelRoom_IdAndMemberAndIsWithPaidFalse(roomId, member);

        return paymentsList.stream().map(TravelPaymentResponseDto::new).collect(Collectors.toList());
    }
}
