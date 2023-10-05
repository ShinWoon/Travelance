package com.easyone.travelance.domain.travel.service;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.respository.ProfileRepository;
import com.easyone.travelance.domain.payment.entity.Payment;
import com.easyone.travelance.domain.payment.repository.PaymentRepository;
import com.easyone.travelance.domain.travel.dto.PaymentResponseDto;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.entity.TravelRoomMember;
import com.easyone.travelance.domain.travel.repository.TravelRoomMemberRepository;
import com.easyone.travelance.domain.travel.repository.TravelRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class TravelPaymentService {

    private final PaymentRepository paymentRepository;
    private final TravelRoomMemberRepository travelRoomMemberRepository;
    private final TravelRoomRepository travelRoomRepository;
    private final ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public List<PaymentResponseDto> findByTravelId(Member member, Long roomId) {
        log.info("개인내역!! ---"+member.getId()+"룸정보"+roomId);
        List<Payment> payments = paymentRepository.findByTravelRoomIdAndIsWithPaidIsTrue(roomId);
        return getPaymentResponseDtos(member, roomId, payments);

    }

    @Transactional(readOnly = true)
    public List<PaymentResponseDto> findByTravelIdAndMemberId(Member member, Long roomId) {
        log.info("공금내역!! ---"+member.getId()+"룸정보"+roomId);
        List<Payment> payments = paymentRepository.findAllByTravelRoom_IdAndMemberAndIsWithPaidTrue(roomId, member);
        return getPaymentResponseDtos(member, roomId, payments);
    }

    @NotNull
    private List<PaymentResponseDto> getPaymentResponseDtos(Member member, Long roomId, List<Payment> payments) {
        TravelRoom travelRoom = travelRoomRepository.findByIdAndMemberId(roomId, member.getId())
                .orElseThrow(()-> new IllegalArgumentException("사용자의 여행방이 없습니다. id =" + roomId));

        ArrayList<PaymentResponseDto> paymentArrayList = new ArrayList<>();

        for (Payment payment: payments) {
            log.info("payment문제인가!! ---"+payment.getPaymentContent());
            String profileUrl = profileRepository.findByMemberAndTravelRoom(payment.getMember(), travelRoom).getProfileUrl();
            TravelRoomMember travelRoomMember = travelRoomMemberRepository.findByTravelRoomAndMember(travelRoom, payment.getMember())
                    .orElseThrow(()-> new IllegalArgumentException("사용자가" + member.getId() + "이 여행방에 없습니다" + roomId));
            Long id = payment.getMember().getId();
            //이 멤버로 여행방과 일치하는 프로필 가져오기
            PaymentResponseDto paymentResponseDto = new PaymentResponseDto(payment, id, travelRoomMember.getTravelNickName(), profileUrl);
            paymentArrayList.add(paymentResponseDto);
        }
        return paymentArrayList;
    }

    @Transactional(readOnly = true)
    public Long TotalPriceTravelId(Long roomId) {
//        List<Payment> payments = paymentRepository.findByTravelRoomId(roomId);
        List<Payment> payments = paymentRepository.findByTravelRoomIdAndIsWithPaidIsTrue(roomId);

        if (payments.size()!=0) {
            Long totalprice = payments.stream()
                    .mapToLong(Payment::getPaymentAmount)
                    .sum();
            return totalprice;
        }
        else {
            return 0L;
        }

    }



}
