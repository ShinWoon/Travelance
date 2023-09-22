package com.easyone.travelance.domain.travel.controller;


import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.service.MemberService;
import com.easyone.travelance.domain.travel.dto.*;
import com.easyone.travelance.domain.travel.service.TravelService;
import com.easyone.travelance.global.memberInfo.MemberInfo;
import com.easyone.travelance.global.memberInfo.MemberInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("travel/room")
public class TravelController {

    private final TravelService travelService;
    private final MemberService memberService;

    // 방 만들기
    @Operation(summary = "여행방 생성", description = "요청 시, 채팅방을 만듭니다. ")
    @PostMapping(value = "")
    public ResponseEntity<String> MakeRoom(@RequestBody RoomInfoRequestDto roomInfoRequestDto) {
        String responseDto= travelService.save(roomInfoRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 친구 초대 : 모바일에서 방과 유저정보를 주면, 방에 유저를 저장하고, 그 방에 있는 유저리스트를 전달
    @Operation(summary = "여행방 친구들 초대하기", description = "프로필 사진, 닉네임 정보와 함께 요청하면, 여행참가자가 되며 유저리스트를 전달합니다. ")
    @PostMapping(value = "/{roomId}/addUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<RoomUserResponseDto>> AddUser(@MemberInfo MemberInfoDto memberInfo, @RequestBody String profileUrl, @PathVariable Long roomId) {
        Member member = memberService.findMemberByEmail(memberInfo.getEmail());

        List<RoomUserResponseDto> roomUserResponseDto=travelService.adduser(roomId, member, profileUrl);
        return new ResponseEntity<>(roomUserResponseDto, HttpStatus.OK);
    }


    //여행방 전체 리스트
    @Operation(summary = "여행 전체 리스트 조회", description = "요청 시, 채팅방 전체리스트를 조회합니다. " +
            "travelName: 여행이름, location: 여행장소, startDate:여행시작일, endDate: 여행종료일, budget: 예산")
    @GetMapping(value = "")
    public ResponseEntity<List<RoomAllResponseDto>> findAllDesc() {
        List<RoomAllResponseDto> responseDtos = travelService.findAllDesc();
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    //여행 방 조회
    // 예산 카테고리 분류 통계 추후 추가
    @Operation(summary = "특정 여행방 조회하기", description = "요청 시, 채팅방을 조회합니다. ")
    @GetMapping(value = "/{roomId}")
    public ResponseEntity<RoomStaticResponseDto> findById(@MemberInfo MemberInfoDto memberInfo, @PathVariable Long roomId) {
        Member member = memberService.findMemberByEmail(memberInfo.getEmail());

        RoomStaticResponseDto responseDto=  travelService.findById(roomId, member);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

//     여행 방 수정
    @Operation(summary = "여행방 수정", description = "요청 시, 채팅방 정보를 수정할 수 있습니다. ")
    @PatchMapping(value = "/{roomId}")
    public ResponseEntity<Void> updateRoom(@MemberInfo MemberInfoDto memberInfo, @PathVariable Long roomId, @RequestBody RoomInfoRequestDto roomInfoRequestDto) {
        Member member = memberService.findMemberByEmail(memberInfo.getEmail());

        travelService.updateRoom(roomInfoRequestDto, roomId, member);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 여행 방 삭제
    @Operation(summary = "여행방 삭제", description = "요청 시, 채팅방 정보를 삭제할 수 있습니다. ")
    @DeleteMapping(value = "/{roomId}")
    public ResponseEntity<Void> deleteRoom(@MemberInfo MemberInfoDto memberInfo, @PathVariable Long roomId) {
        Member member = memberService.findMemberByEmail(memberInfo.getEmail());

        travelService.delete(roomId, member);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //여행 공지사항 등록



    //여행 맵 표시








}
