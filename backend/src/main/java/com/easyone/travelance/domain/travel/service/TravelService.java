package com.easyone.travelance.domain.travel.service;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.respository.MemberRepository;
import com.easyone.travelance.domain.travel.dto.RoomAllResponseDto;
import com.easyone.travelance.domain.travel.dto.RoomInfoRequestDto;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.enumclass.RoomType;
import com.easyone.travelance.domain.travel.repository.TravelRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TravelService {
    private final TravelRoomRepository travelRoomRepository;
    private final MemberRepository memberRepository;

    //방만들기
    @Transactional
    public void save(RoomInfoRequestDto roomInfoRequestDto) {
        //추후 변경
        Long memberid =1L;
        Member member = memberRepository.findById(memberid)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. id =" + memberid));

        //방 만든 직전에는 사전정산 상태
        RoomType roomType = RoomType.BEFORE;
        TravelRoom room = travelRoomRepository.save(roomInfoRequestDto.toEntity(roomType, member));
    }

    public List<RoomAllResponseDto> findAllDesc() {
        return travelRoomRepository.findAllOrderByIdDesc().stream()
                .map(RoomAllResponseDto::new)
                .collect(Collectors.toList());
    }
}
