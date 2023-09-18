package com.easyone.travelance.domain.travel.controller;


import com.easyone.travelance.domain.member.dto.ProfileImageRequestDto;
import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.respository.MemberRepository;
import com.easyone.travelance.domain.travel.dto.*;
import com.easyone.travelance.domain.travel.service.TravelService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("travel/room")
public class TravelController {

    private final TravelService travelService;
    private final MemberRepository memberRepository;

    // 방 만들기
    @Operation(summary = "여행방 만들기", description = "요청 시, 채팅방을 만듭니다. " +
            "travelName: 여행이름, location: 여행장소, startDate:여행시작일, endDate: 여행종료일, budget: 예산")
    @PostMapping(value = "")
    public ResponseEntity<?> MakeRoom(@RequestBody RoomInfoRequestDto roomInfoRequestDto) {
        /** 추후변경 */
        Long memberid =1L;
        Member member = memberRepository.findById(memberid)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. id =" + memberid));

        travelService.save(roomInfoRequestDto, member);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 프로필 설정
    @Operation(summary = "여행방에서 내 프로필사진 설정", description = "요청 시, 채팅방에 유저의 프로필 사진을 설정할 수 있습니다.")
    @PostMapping(value = "/{roomId}/setProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> setProfile(@RequestBody String profileUrl, @PathVariable Long roomId) {
        /** 추후변경 */
        Long memberid =1L;
        Member member = memberRepository.findById(memberid)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. id =" + memberid));

        travelService.setProfile(roomId, member, profileUrl);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    // 친구 초대 : 모바일에서 방과 유저정보를 주면, 방에 유저를 저장하고, 그 방에 있는 유저리스트를 전달
    @Operation(summary = "여행방 친구들 초대하기", description = "요청 시, 채팅방에 친구들을 초대하고, 채팅방의 유저리스트를 전달합니다. ")
    @PostMapping(value = "/{roomId}/addUser")
    public ResponseEntity<List<RoomUserResponseDto>> AddUser(@PathVariable Long roomId) {
        /** 추후변경 */
        Long memberid =1L;
        Member member = memberRepository.findById(memberid)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. id =" + memberid));

        List<RoomUserResponseDto> roomUserResponseDto=travelService.adduser(roomId, member);
        return new ResponseEntity<>(roomUserResponseDto, HttpStatus.OK);
    }


    //여행방 전체 리스트
    @Operation(summary = "여행 전체 리스트 조회", description = "요청 시, 채팅방 전체리스트를 조회합니다. " +
            "travelName: 여행이름, location: 여행장소, startDate:여행시작일, endDate: 여행종료일, budget: 예산")
    @GetMapping(value = "")
    public ResponseEntity<List<RoomAllResponseDto>> findAllDesc() {
        /** 추후변경 */
        Long memberid =1L;
        Member member = memberRepository.findById(memberid)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. id =" + memberid));

        List<RoomAllResponseDto> responseDtos = travelService.findAllDesc(member);
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    //여행 방 조회
    // 예산 카테고리 분류 통계 추후 추가
    @Operation(summary = "특정 여행방 조회하기", description = "요청 시, 채팅방을 조회합니다. ")
    @GetMapping(value = "/{roomId}")
    public ResponseEntity<RoomStaticResponseDto> findById(@PathVariable Long roomId) {
        /** 추후변경 */
        Long memberid =1L;
        Member member = memberRepository.findById(memberid)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. id =" + memberid));

        RoomStaticResponseDto responseDto=  travelService.findById(roomId, member);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

//     여행 방 수정
    @Operation(summary = "여행방 수정", description = "요청 시, 채팅방 정보를 수정할 수 있습니다. ")
    @PatchMapping(value = "/{roomId}")
    public ResponseEntity<Void> updateRoom(@PathVariable Long roomId, @RequestBody RoomInfoRequestDto roomInfoRequestDto) {
        /** 추후변경 */
        Long memberid =1L;
        Member member = memberRepository.findById(memberid)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. id =" + memberid));

        travelService.updateRoom(roomInfoRequestDto, roomId, member);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 여행 방 삭제
    @Operation(summary = "여행방 삭제", description = "요청 시, 채팅방 정보를 삭제할 수 있습니다. ")
    @DeleteMapping(value = "/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId) {
        /** 추후변경 */
        Long memberid =1L;
        Member member = memberRepository.findById(memberid)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. id =" + memberid));

        travelService.delete(roomId, member);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //여행 공지사항 등록



    //여행 맵 표시








}
