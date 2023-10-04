package com.easyone.travelance.domain.travel.controller;


import com.easyone.travelance.domain.common.ResultDto;
import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.respository.MemberRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/travel/room")
public class TravelController {

    private final TravelService travelService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    // 방 만들기
    @Operation(summary = "여행방 생성", description = "여행방 만든 사용자의 프로필 이미지를 요청하면, 채팅방을 만들고, 채팅방의 참여자에 추가시킵니다." +
            "<요청 값> profileUrl: 호스트 프로필사진, nickName: 호스트 닉네임, startdate: 시작시간, enddate: 끝시간, startDate:여행시작일, endDate: 여행종료일, budget: 예산" +
            "<응답 값> result: 방id(string값)")
    @PostMapping(value = "")
    public ResponseEntity<RoomIdResponseDto> MakeRoom(@MemberInfo MemberInfoDto memberInfo,
                                                      @RequestPart RoomInfoRequestDto roomInfoRequestDto,
                                                      @RequestPart(value = "imageFiles", required = false) MultipartFile imageFiles,
                                                      @RequestPart RoomUserRequestDto roomUserRequestDto) throws Exception {

        Member member = memberService.findMemberByEmail(memberInfo.getEmail());

        RoomIdResponseDto roomIdResponseDto= travelService.save(roomInfoRequestDto, member, roomUserRequestDto, imageFiles);

        return new ResponseEntity<>(roomIdResponseDto,HttpStatus.CREATED);
    }

    // 프로필 설정, 내 방에 맞는 프로필을 저장함(build test)
    @Operation(summary = "내 프로필 설정하기", description = "방에 입장하고 싶은 맴버 정보와 프로필 사진을 요청하면, 여행참가자가 되며 유저리스트를 전달합니다. ")
    @PostMapping(value = "/{roomId}/addUser")
    public ResponseEntity <ResultDto> AddUser(@MemberInfo MemberInfoDto memberInfo,
                                             @RequestPart(value = "imageFiles", required = false) MultipartFile profileUrl,
                                               @PathVariable Long roomId,
                                               @RequestPart RoomUserRequestDto roomUserRequestDto) throws Exception {
        Member member = memberService.findMemberByEmail(memberInfo.getEmail());
        ResultDto resultDto =travelService.adduser(roomId, member, roomUserRequestDto, profileUrl);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    //친구 목록 조회: 현재 여행방의 초대한 맴버(이미 프로필 설정까지 완료된)를 조회할 수 있는 컨트롤러
    @Operation(summary = "여행방 친구 조회하기", description = "여행하고 있는 유저리스트를 전달합니다.")
    @GetMapping(value = "/{roomId}/UserList")
    public ResponseEntity<List<RoomUserResponseDto>> GetUserList( @PathVariable Long roomId) {

        List<RoomUserResponseDto> roomUserResponseDto=travelService.getUserList(roomId);
        return new ResponseEntity<>(roomUserResponseDto, HttpStatus.OK);
    }

    //여행방 전체 리스트
    @Operation(summary = "여행 전체 리스트 조회", description = "요청 시, 유저에 해당하는 채팅방 전체리스트를 조회합니다. " +
            "travelName: 여행이름, startDate:여행시작일, endDate: 여행종료일, budget: 예산")
    @GetMapping(value = "")
    public ResponseEntity<List<RoomAllResponseDto>> findAllDesc(@MemberInfo MemberInfoDto memberInfo) {

        Member member = memberService.findMemberByEmail(memberInfo.getEmail());

        List<RoomAllResponseDto> responseDtos = travelService.findAllDesc(member);
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    //여행 방 조회
    @Operation(summary = "특정 여행방 조회하기", description = "요청 시, 채팅방을 조회합니다. " +
            "member정보를 받아서 여행방에서 내가 쓴 목록과 전체 목록을 구분합니다.")
    @GetMapping(value = "/{roomId}")
    public ResponseEntity<RoomStaticResponseDto> findById(@MemberInfo MemberInfoDto memberInfo, @PathVariable Long roomId) {

        Member member = memberService.findMemberByEmail(memberInfo.getEmail());
        RoomStaticResponseDto responseDto=  travelService.findById(roomId, member);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

//     여행 방 수정
    @Operation(summary = "여행방 수정", description = "요청 시, 채팅방 정보를 수정할 수 있습니다. ")
    @PatchMapping(value = "/{roomId}")
    public ResponseEntity<ResultDto> updateRoom(@PathVariable Long roomId, @RequestBody RoomInfoRequestDto roomInfoRequestDto) {
        ResultDto resultDto = travelService.updateRoom(roomInfoRequestDto, roomId);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    // 여행 방 나가기
    @Operation(summary = "여행방 나가기", description = "맴버정보와 room정보를 함께 요청 시, 사용자는 채팅방을 나갑니다. ")
    @DeleteMapping(value = "/{roomId}")
    public ResponseEntity<ResultDto> deleteRoom(@PathVariable Long roomId, @MemberInfo MemberInfoDto memberInfo ) {
        Member member = memberService.findMemberByEmail(memberInfo.getEmail());
        ResultDto resultDto = travelService.delete(roomId, member);
        return new ResponseEntity<>(resultDto,HttpStatus.OK);
    }

    //여행 시작
    @Operation(summary = "여행시작", description = "요청 시, 방의 여행이 시작되며, 정산중으로 상태가 바뀝니다. ")
    @PostMapping(value = "/{roomId}/start")
    public ResponseEntity<ResultDto> startTravel(@MemberInfo MemberInfoDto memberInfo, @PathVariable Long roomId) {
        Member member = memberService.findMemberByEmail(memberInfo.getEmail());
        ResultDto resultDto = travelService.startTravel(roomId, member);
        return new ResponseEntity<>(resultDto,HttpStatus.OK);
    }



}
