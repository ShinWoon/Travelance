package com.easyone.travelance.domain.travel.service;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.payment.entity.Payment;
import com.easyone.travelance.domain.payment.repository.PaymentRepository;
import com.easyone.travelance.domain.travel.dto.MapAllResponseDto;
import com.easyone.travelance.domain.travel.dto.MapDetailRequestDto;
import com.easyone.travelance.domain.travel.dto.MapDetailResponseDto;
import com.easyone.travelance.domain.travel.entity.TravelRoomMember;
import com.easyone.travelance.domain.travel.repository.TravelRoomMemberRepository;
import com.easyone.travelance.domain.travel.repository.TravelRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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
    private final TravelRoomMemberRepository travelRoomMemberRepository;
    private final PaymentRepository paymentRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "mapLists", key="#roomId")
    public List<MapAllResponseDto> mapList(Long roomId) {

        //추후 완료된 여행방에 대한 위치정보도 반환해야하기 때문에 roomId를 받아서 전달!

//            List<TravelRoom> travelRoomMemberList = travelRoomRepository.findAllByTravelRoomMembersMember(member);
//            //맴버의 현재 여행방인 곳 찾기
//            for (TravelRoom travelRoomIsDone : travelRoomMemberList) {
//                if (travelRoomIsDone.getIsDone()== RoomType.NOW) {
//                    roomId=travelRoomIsDone.getId();
//                }
//            }

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

    @Transactional(readOnly = true)
    @Cacheable(value = "mapDetails", key = "#roomId + '-' + #mapDetailRequestDto.storeAddress")
    public List<MapDetailResponseDto> mapDetail(Long roomId, MapDetailRequestDto mapDetailRequestDto) {
        //dto에서 map의 주소를 가져와서 이 여행방의 이 장소에서 결제된 내역을 모두 가져와서 반환한다.

        List<Payment> paymentList = paymentRepository.findByTravelRoomIdAndStoreAddress(roomId, mapDetailRequestDto.getStoreAddress());

        List<MapDetailResponseDto> mapDetailResponseDtoList = new ArrayList<>();

        for (Payment payment : paymentList) {
            TravelRoomMember travelRoomMember = travelRoomMemberRepository.findByTravelRoomAndMember(payment.getTravelRoom(), payment.getMember())
                    .orElseThrow(()-> new IllegalArgumentException("사용자의 여행방이 없습니다. id =" + roomId));

            String nickName = travelRoomMember.getTravelNickName();
            MapDetailResponseDto mapDetailResponseDto = new MapDetailResponseDto(payment, nickName);
            mapDetailResponseDtoList.add(mapDetailResponseDto);
        }
        return mapDetailResponseDtoList;
    }
}
