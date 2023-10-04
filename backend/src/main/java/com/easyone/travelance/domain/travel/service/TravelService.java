package com.easyone.travelance.domain.travel.service;

import com.easyone.travelance.domain.common.ResultDto;
import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.respository.ProfileRepository;
import com.easyone.travelance.domain.travel.dto.*;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.entity.TravelRoomMember;
import com.easyone.travelance.domain.travel.enumclass.RoomType;
import com.easyone.travelance.domain.travel.repository.TravelRoomMemberRepository;
import com.easyone.travelance.domain.travel.repository.TravelRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
    public RoomIdResponseDto save(RoomInfoRequestDto roomInfoRequestDto, Member member, RoomUserRequestDto roomUserRequestDto) throws Exception {
        //방 만든 직전에는 사전정산 상태
        RoomType roomType = RoomType.BEFORE;

        TravelRoom travelRoom = roomInfoRequestDto.toEntity(roomType);
        travelRoomRepository.save(travelRoom);

//            if (profileUrl != null) {
        travelProfileService.saveImage(travelRoom, "https://i.ibb.co/9WLnW7t/20221014514371.jpg", member);
//            }

        TravelRoomMember travelRoomMember = TravelRoomMember.builder()
                .travelRoom(travelRoom)
                .member(member)
                .nickName(roomUserRequestDto.getNickName())
                .isDone(false)
                .build();

        travelRoomMemberRepository.save(travelRoomMember);

        return new RoomIdResponseDto(travelRoom.getId().toString());
    }


    //유저가 방에 추가되어 닉네임과 사진을 설정하고, 친구 목록을 반환
    //Profileurl도 같이 반환
    @Transactional
    public ResultDto adduser(Long roomId, Member member, RoomUserRequestDto roomUserRequestDto) throws Exception {

        TravelRoom travelRoom = travelRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 여행방이 없습니다. id =" + roomId));

        //현재 정산중 방이고, 그 멤버가 정산중 방이 있을 때

        List<TravelRoom> travelRoomMemberList = travelRoomRepository.findAllByTravelRoomMembersMember(member);
        for (TravelRoom travelRoomIsDone : travelRoomMemberList) {
            if ((travelRoom.getIsDone()==RoomType.NOW && travelRoomIsDone.getIsDone()==RoomType.NOW) || travelRoomMemberRepository.findByTravelRoomAndMember(travelRoom, member).isPresent() ) {
                return new ResultDto("0");
            }
        }


            //프로필 사진이 있으면, 프로필 사진 저장
//            if (profileUrl != null) {
                travelProfileService.saveImage(travelRoom, "https://i.ibb.co/9WLnW7t/20221014514371.jpg", member);
//            }

        TravelRoomMember travelRoomMember = TravelRoomMember.builder()
                .travelRoom(travelRoom)
                .member(member)
                .isDone(false)
                .nickName(roomUserRequestDto.getNickName())
                .build();

        travelRoomMemberRepository.save(travelRoomMember);
        return new ResultDto("참여자 방에 저장");

    }

    //유저에 해당하는 방만 보내주기
    @Transactional(readOnly = true)
    @Cacheable(value = "roomCacheAll", key = "#member.id")
    public List<RoomAllResponseDto> findAllDesc(Member member) {
        // travelroommember도메인에서 member에 해당하는  travelroom을 RoomAllResponseDto로 모두 반환

        List<TravelRoom> travelRoomMemberList = travelRoomRepository.findAllByTravelRoomMembersMember(member);

        List<RoomAllResponseDto> roomAllResponseDtos = new ArrayList<>();
        for (TravelRoom travelRoom : travelRoomMemberList) {
            Long totalPrice = travelPaymentService.TotalPriceTravelId(travelRoom.getId());
            RoomAllResponseDto responseDto = new RoomAllResponseDto(travelRoom, totalPrice);
            roomAllResponseDtos.add(responseDto);
        }
        return roomAllResponseDtos;
    }


    @Transactional(readOnly = true)
    @Cacheable(value = "roomCache", key = "#roomId")
    public RoomStaticResponseDto findById(Long roomId, Member member) {

        TravelRoom travelRoom = travelRoomRepository.findByIdAndMemberId(roomId, member.getId())
                .orElseThrow(()-> new IllegalArgumentException("사용자의 여행방이 없습니다. id =" + roomId));

        Long budget = travelRoom.getBudget();
        Long UseTotal = travelPaymentService.TotalPriceTravelId(roomId);

        //예산 대비 사용 비율
        double budgetPer = ((double) UseTotal / budget) * 1000.0;
        budgetPer = Math.floor(budgetPer) / 1000.0;

        // 남는 금액
        Long rest = budget-UseTotal;

        //전체 소비내역
        List<PaymentResponseDto> everyUse = travelPaymentService.findByTravelId(member, roomId);

        //내 소비내역
        List<PaymentResponseDto> myUse = travelPaymentService.findByTravelIdAndMemberId(member, roomId);

        return new RoomStaticResponseDto(travelRoom, budgetPer, UseTotal, rest, everyUse, myUse);
    }

    @Transactional
    @CacheEvict(value = "roomCache", key = "#roomId")
    public ResultDto updateRoom(RoomInfoRequestDto roomInfoRequestDto, Long roomId) {

        TravelRoom travelRoom = travelRoomRepository.findById(roomId)
                .orElseThrow(()-> new IllegalArgumentException("사용자의 여행방이 없습니다. id =" + roomId));

        try {
            travelRoom.update(roomInfoRequestDto);
            return new ResultDto("여행방 수정 성공");
        }
        catch (Exception e) {
            return new ResultDto("여행방 수정 실패");
        }

    }

    @Transactional
    @CacheEvict(value = "roomCache", key = "#roomId")
    public ResultDto delete(Long roomId, Member member) {

        TravelRoom travelRoom = travelRoomRepository.findByIdAndMemberId(roomId, member.getId())
                .orElseThrow(()-> new IllegalArgumentException("사용자의 여행방이 없습니다. id =" + roomId));
        TravelRoomMember travelRoomMember = travelRoomMemberRepository.findByTravelRoomAndMember(travelRoom, member)
                .orElseThrow(() -> new IllegalArgumentException("해당 여행방에 맴버가 없습니다. Roomid =" + roomId + "memberId"+member.getId()));;

        if (travelRoomMember!=null) {
            travelRoomMemberRepository.delete(travelRoomMember);
            return new ResultDto("여행방 나가기 성공");
        }
        else {
            return new ResultDto("여행방 나가기 실패");
        }

    }

    @Transactional(readOnly = true)
    @Cacheable(value = "userLists", key="#roomId")
    public List<RoomUserResponseDto> getUserList(Long roomId) {
        TravelRoom travelRoom = travelRoomRepository.findById(roomId)
                .orElseThrow(()-> new IllegalArgumentException("사용자의 여행방이 없습니다. id =" + roomId));

        List<RoomUserResponseDto> travelRoomMemberList = new ArrayList<>();

        List<TravelRoomMember> travelRoomMember = travelRoomMemberRepository.findAllByTravelRoom(travelRoom);
        for (TravelRoomMember travelRoomMember1 : travelRoomMember) {
            TravelRoom travelRoom1 = travelRoomMember1.getTravelRoom();
            Member member = travelRoomMember1.getMember();
            String nickname = travelRoomMember1.getTravelNickName();

            //여행방별로 저장된 멤버 이미지를 가져오기
            String profileUrl = profileRepository.findByMemberAndTravelRoom(member, travelRoom1).getProfileUrl();
            RoomUserResponseDto roomUserResponseDto = new RoomUserResponseDto(member, nickname, profileUrl);
            travelRoomMemberList.add(roomUserResponseDto);
        }


        return travelRoomMemberList;
    }

    @Transactional
    @CacheEvict(value = "roomCache", key = "#roomId")
    public ResultDto startTravel(Long roomId, Member member) {
        try {
            TravelRoom travelRoom = travelRoomRepository.findByIdAndMemberId(roomId, member.getId())
                    .orElseThrow(()-> new IllegalArgumentException("사용자의 여행방이 없습니다. id =" + roomId));
            //기존 정산중인 목록이 있으면, 0반환
            List<TravelRoom> travelRoomMemberList = travelRoomRepository.findAllByTravelRoomMembersMember(member);
            for (TravelRoom travelRoomIsDone : travelRoomMemberList) {
                if (travelRoomIsDone.getIsDone()==RoomType.NOW) {
                    return new ResultDto("0");
                }
            }

            travelRoom.setRoomType(RoomType.NOW);
            return new ResultDto(roomId.toString());
        }
        catch (Exception e) {
            return new ResultDto("여행방 시작 실패");
        }

    }
}
