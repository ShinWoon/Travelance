package com.easyone.travelance.domain.travel.service;

import com.easyone.travelance.domain.common.ResultDto;
import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.payment.entity.Payment;
import com.easyone.travelance.domain.payment.repository.PaymentRepository;
import com.easyone.travelance.domain.travel.dto.MapAllResponseDto;
import com.easyone.travelance.domain.travel.dto.PaymentResponseDto;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.enumclass.RoomType;
import com.easyone.travelance.domain.travel.repository.TravelRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class MapService {
    private final TravelRoomRepository travelRoomRepository;
    private final PaymentRepository paymentRepository;

    @Transactional(readOnly = true)
    public List<MapAllResponseDto> mapList(Member member) {

            List<TravelRoom> travelRoomMemberList = travelRoomRepository.findAllByTravelRoomMembersMember(member);
            //맴버의 현재 여행방인 곳 찾기
            Long roomId=0L;
            for (TravelRoom travelRoomIsDone : travelRoomMemberList) {
                if (travelRoomIsDone.getIsDone()== RoomType.NOW) {
                    roomId=travelRoomIsDone.getId();
                }
            }

            // 모든 결제내역의 위치장소를 리스트로 반환
            List<Payment> paymentResponseDtoList = paymentRepository.findByTravelRoomId(roomId);

            //결제장소는 중복되지 않음
            Set<MapAllResponseDto> paymentLocation = new HashSet<>();

            for (Payment location : paymentResponseDtoList) {
                paymentLocation.add(new MapAllResponseDto(location));
            }

            //set을 list로 변환해서 반환
            List<MapAllResponseDto> paymentLocationList = new ArrayList<>(paymentLocation);

            return paymentLocationList;

    }


}
