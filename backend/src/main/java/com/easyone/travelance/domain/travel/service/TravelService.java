package com.easyone.travelance.domain.travel.service;

import com.easyone.travelance.domain.common.ResultDto;
import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.entity.Profile;
import com.easyone.travelance.domain.member.respository.ProfileRepository;
import com.easyone.travelance.domain.travel.dto.*;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.entity.TravelRoomMember;
import com.easyone.travelance.domain.travel.enumclass.RoomType;
import com.easyone.travelance.domain.travel.repository.TravelRoomMemberRepository;
import com.easyone.travelance.domain.travel.repository.TravelRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class TravelService {
    private final TravelRoomRepository travelRoomRepository;
    private final TravelRoomMemberRepository travelRoomMemberRepository;
    private final TravelPaymentService travelPaymentService;
    private final ProfileRepository profileRepository;
    private final TravelProfileService travelProfileService;

    //방만들기
    @Transactional
    public ResultDto save(RoomInfoRequestDto roomInfoRequestDto) {
        //방 만든 직전에는 사전정산 상태
        RoomType roomType = RoomType.BEFORE;
        try {
            TravelRoom travelRoom = roomInfoRequestDto.toEntity(roomType);
            travelRoomRepository.save(roomInfoRequestDto.toEntity(roomType));
            return new ResultDto("여행방 생성 성공");
        }
        catch (Exception e) {
            return new ResultDto("여행방 생성 실패");
        }
    }

    //유저가 방에 추가되어 닉네임과 사진을 설정하고, 친구 목록을 반환
    //Profileurl도 같이 반환
    @Transactional
    public List<RoomUserResponseDto> adduser(Long roomId, Member member, MultipartFile imageFile) {

        TravelRoom travelRoom = travelRoomRepository.findById(roomId)
                .orElseThrow(()-> new IllegalArgumentException("해당 여행방이 없습니다. id =" + roomId));

        //프로필 사진이 있으면, 프로필 사진 저장
        if(imageFile!=null) {
            travelProfileService.saveImage(travelRoom,imageFile);
        }

        TravelRoomMember travelRoomMember = TravelRoomMember.builder()
                .travelRoom(travelRoom)
                .member(member)
                .build();

        travelRoomMemberRepository.save(travelRoomMember);

        List<RoomUserResponseDto> travelRoomMemberList = travelRoomMemberRepository.findAllByTravelRoom(travelRoom)
                                        .stream().map(user -> new RoomUserResponseDto().builder().member(user.getMember())
                                        .build()).collect(Collectors.toList());

        return travelRoomMemberList;
    }

    //유저에 해당하는 방만 보내주기 -
    @Transactional(readOnly = true)
    public List<RoomAllResponseDto> findAllDesc(Member member) {
        // travelroommember도메인에서 member에 해당하는  travelroom을 RoomAllResponseDto로 모두 반환

        List<TravelRoomMember> travelRoomMemberList = travelRoomMemberRepository.findAllByMember(member);

        List<RoomAllResponseDto> roomAllResponseDtos = new ArrayList<>();
        for (TravelRoomMember travelroommember : travelRoomMemberList) {
            TravelRoom travelRoom = travelroommember.getTravelRoom();
            Long totalPrice = travelPaymentService.TotalPriceTravelId(travelRoom.getId());
            RoomAllResponseDto responseDto = new RoomAllResponseDto(travelRoom, totalPrice);
            roomAllResponseDtos.add(responseDto);
        }
        return roomAllResponseDtos;
    }


    @Transactional(readOnly = true)
    public RoomStaticResponseDto findById(Long roomId, Member member) {

        TravelRoom travelRoom = travelRoomRepository.findById(roomId)
                .orElseThrow(()-> new IllegalArgumentException("해당 여행방이 없습니다. id =" + roomId));

        Long budget = travelRoom.getBudget();
        Long UseTotal = travelPaymentService.TotalPriceTravelId(roomId);

        //예산 대비 사용 비율
        Long budgetPer = UseTotal / budget;
        // 남는 금액
        Long rest = budget-UseTotal;

        //전체 소비내역
        List<PaymentResponseDto> everyUse = travelPaymentService.findByTravelId(member, roomId);

        //내 소비내역
        List<PaymentResponseDto> myUse = travelPaymentService.findByTravelIdAndMemberId(member, roomId);

        return new RoomStaticResponseDto(travelRoom, budgetPer, UseTotal, rest, everyUse, myUse);
    }

    @Transactional
    public ResultDto updateRoom(RoomInfoRequestDto roomInfoRequestDto, Long roomId) {

        TravelRoom travelRoom = travelRoomRepository.findById(roomId)
                .orElseThrow(()-> new IllegalArgumentException("해당 여행방이 없습니다. id =" + roomId));

        try {
            travelRoom.update(roomInfoRequestDto);
            return new ResultDto("여행방 수정 성공");
        }
        catch (Exception e) {
            return new ResultDto("여행방 수정 실패");
        }

    }

    @Transactional
    public ResultDto delete(Long roomId, Member member) {

        TravelRoom travelRoom = travelRoomRepository.findById(roomId)
                .orElseThrow(()-> new IllegalArgumentException("해당 여행방이 없습니다. id =" + roomId));
        TravelRoomMember travelRoomMember = travelRoomMemberRepository.findByTravelRoomAndMember(travelRoom, member);

       if (travelRoomMember!=null) {
           travelRoomMemberRepository.delete(travelRoomMember);
            return new ResultDto("여행방 나가기 성공");
        }
        else {
            return new ResultDto("여행방 나가기 실패");
        }


    }



}
