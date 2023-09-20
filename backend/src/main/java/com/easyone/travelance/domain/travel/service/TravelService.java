package com.easyone.travelance.domain.travel.service;

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
    public void save(RoomInfoRequestDto roomInfoRequestDto) {
        //방 만든 직전에는 사전정산 상태

        RoomType roomType = RoomType.BEFORE;


        TravelRoom travelRoom =roomInfoRequestDto.toEntity(roomType);
        travelRoomRepository.save(roomInfoRequestDto.toEntity(roomType));
        log.info(travelRoom.getTravelName());
        log.info(roomInfoRequestDto.getTravelName());
    }

    @Transactional(readOnly = true)
    public List<RoomAllResponseDto> findAllDesc() {
        return travelRoomRepository.findAll().stream()
                .map(entity -> {
                  Long totalPrice = travelPaymentService.TotalPriceTravelId(entity.getId());
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

    public  List<RoomUserResponseDto> adduser(Long roomId, Member member) {
        //유저를 travelroommember에 추가하고, 리스트를 반한한다.

        TravelRoom travelRoom = travelRoomRepository.findById(roomId)
                .orElseThrow(()-> new IllegalArgumentException("해당 여행방이 없습니다. id =" + roomId));

        TravelRoomMember travelRoomMember = TravelRoomMember.builder()
                        .travelRoom(travelRoom)
                        .member(member)
                        .build();
        travelRoomMemberRepository.save(travelRoomMember);
        Profile profile = Profile.builder()
                            .member(member)
                            .travelRoom(travelRoom).build();

        return travelRoomMemberRepository.findAll().stream()
                .map(entity -> {
                    return new RoomUserResponseDto(member, profile);})
                .collect(Collectors.toList());

    }

    //..?
    public void setProfile(Long roomId, Member member, String profileUrl) {
        TravelRoom travelRoom = travelRoomRepository.findById(roomId)
                .orElseThrow(()-> new IllegalArgumentException("해당 여행방이 없습니다. id =" + roomId));

        if(profileUrl!=null) {
            //프로필 사진을 저장한다
            Profile profile = Profile.builder()
                            .travelRoom(travelRoom)
                            .profileUrl(profileUrl)
                            .member(member).build();

            profileRepository.save(profile);
        }
    }
}
