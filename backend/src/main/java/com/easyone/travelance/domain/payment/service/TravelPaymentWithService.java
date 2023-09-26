package com.easyone.travelance.domain.payment.service;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.payment.entity.Payment;
import com.easyone.travelance.domain.payment.repository.PaymentRepository;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.enumclass.RoomType;
import com.easyone.travelance.domain.travel.repository.TravelRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TravelPaymentWithService {
    @Autowired
    private TravelRoomRepository travelRoomRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getPaymentWith(Member member) {

        // 1. 현재 회원이 속한 여행방 중에서 RoomType이 NOW인 것을 조회
        List<TravelRoom> travelRooms = travelRoomRepository.findAllByTravelRoomMembers_MemberAndIsDone(member, RoomType.NOW);

        if (travelRooms.isEmpty()) {
            throw new EntityNotFoundException("해당 사용자의 NOW 상태의 여행방이 없습니다.");
        }

        // 첫 번째 NOW 상태의 여행방의 ID를 가져옵니다.
        // (멤버가 여러 NOW 상태의 여행방에 속할 경우, 추가적인 로직 필요)
        Long roomId = travelRooms.get(0).getId();

        // 2. roomId와 memberId를 이용해서 Payment 내역 중 withPaid가 True인 것만 조회
        return paymentRepository.findAllByTravelRoom_IdAndMemberAndIsWithPaidTrue(roomId, member);
    }

    public List<Payment> getPaymentAlone(Member member) {

        // 1. 현재 회원이 속한 여행방 중에서 RoomType이 NOW인 것을 조회
        List<TravelRoom> travelRooms = travelRoomRepository.findAllByTravelRoomMembers_MemberAndIsDone(member, RoomType.NOW);

        if (travelRooms.isEmpty()) {
            throw new EntityNotFoundException("해당 사용자의 NOW 상태의 여행방이 없습니다.");
        }

        // 첫 번째 NOW 상태의 여행방의 ID를 가져옵니다.
        // (멤버가 여러 NOW 상태의 여행방에 속할 경우, 추가적인 로직 필요)
        Long roomId = travelRooms.get(0).getId();

        // 2. roomId와 memberId를 이용해서 Payment 내역 중 withPaid가 False인 것만 조회
        return paymentRepository.findAllByTravelRoom_IdAndMemberAndIsWithPaidFalse(roomId, member);
    }
}
