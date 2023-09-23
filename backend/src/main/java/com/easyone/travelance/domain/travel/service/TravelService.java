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
    @Transactional
    public List<RoomUserResponseDto> adduser(Long roomId, Member member, String profileUrl) {

        TravelRoom travelRoom = travelRoomRepository.findById(roomId)
                .orElseThrow(()-> new IllegalArgumentException("해당 여행방이 없습니다. id =" + roomId));

        //프로필 사진이 있으면, 프로필 사진 저장
        if(profileUrl!=null) {
            Profile profile = Profile.builder()
                    .travelRoom(travelRoom)
                    .profileUrl(profileUrl)
                    .member(member)
                    .build();

            profileRepository.save(profile);
        }

        TravelRoomMember travelRoomMember = TravelRoomMember.builder()
                .travelRoom(travelRoom)
                .member(member)
                .build();

        travelRoomMemberRepository.save(travelRoomMember);

        List<TravelRoomMember> travelRoomMemberList = travelRoomMemberRepository.findAllByTravelRoom(travelRoom);

        List<RoomUserResponseDto> list = travelRoomMemberList.stream()
                .map(travelRoomMember1 -> {
                    Profile profile = profileRepository.findByMember(travelRoomMember1.getMember());
                    return RoomUserResponseDto.builder()
                            .member(travelRoomMember1.getMember())
                            .profile(profile)
                            .build();
                })
                .collect(Collectors.toList());

        return list;
    }

    @Transactional(readOnly = true)
    public List<RoomAllResponseDto> findAllDesc() {
        return travelRoomRepository.findAll().stream()
                .map(entity -> {
//                  Long totalPrice = travelPaymentService.TotalPriceTravelId(entity.getId());
                    Long totalPrice =0L;
                    return new RoomAllResponseDto(entity, totalPrice);

                })
                .collect(Collectors.toList());
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
    public void updateRoom(RoomInfoRequestDto roomInfoRequestDto, Long roomId, Member member) {

        TravelRoom travelRoom = travelRoomRepository.findById(roomId)
                .orElseThrow(()-> new IllegalArgumentException("해당 여행방이 없습니다. id =" + roomId));



        /** 추후변경  참여자인 사람은 모두 수정할 수 있도록*/
        if(travelRoomMemberRepository.existsByMember(member)) {
            travelRoom.update(roomInfoRequestDto);
        }
//        else {
////            throw new UserNotAuthorizedException("해당 멤버는 게시글 작성자가 아닙니다.");
//        }

    }

    @Transactional
    public void delete(Long roomId, Member member) {

        TravelRoom travelRoom = travelRoomRepository.findById(roomId)
                .orElseThrow(()-> new IllegalArgumentException("해당 여행방이 없습니다. id =" + roomId));

        /** 추후변경  참여자인 사람은 모두 수정할 수 있도록*/
        if(travelRoomMemberRepository.existsByMember(member)) {
            travelRoomRepository.delete(travelRoom);
        }
//        else {
////            throw new UserNotAuthorizedException("해당 멤버는 게시글 작성자가 아닙니다.");
//        }
    }



}
