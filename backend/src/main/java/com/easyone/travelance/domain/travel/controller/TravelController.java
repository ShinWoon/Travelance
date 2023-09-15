package com.easyone.travelance.domain.travel.controller;


import com.easyone.travelance.domain.travel.dto.RoomAllResponseDto;
import com.easyone.travelance.domain.travel.dto.RoomStaticResponseDto;
import com.easyone.travelance.domain.travel.dto.RoomInfoRequestDto;
import com.easyone.travelance.domain.travel.service.TravelService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("travel/room")
public class TravelController {

    private final TravelService travelService;

    // 방 만들기
    @Operation(summary = "여행방 만들기", description = "요청 시, 채팅방을 만듭니다. " +
            "travelName: 여행이름, location: 여행장소, startDate:여행시작일, endDate: 여행종료일, budget: 예산")
    @PostMapping(value = "")
    public ResponseEntity<?> makeroom(@RequestBody RoomInfoRequestDto roomInfoRequestDto) {
        travelService.save(roomInfoRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 친구 초대(딥링크) 모바일에서 방과 유저정보를 주면, 방에 유저를 저장하고, 그 방에 있는 유저리스트를 전달


    // 프로필 설정



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
    public ResponseEntity<RoomStaticResponseDto> findById(@PathVariable Long roomId) {
        RoomStaticResponseDto responseDto=  travelService.findById(roomId);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

//     여행 방 수정
    @Operation(summary = "여행방 수정", description = "요청 시, 채팅방 정보를 수정할 수 있습니다. ")
    @PatchMapping(value = "/{roomId}")
    public ResponseEntity<Void> updateRoom(@PathVariable Long roomId, @RequestBody RoomInfoRequestDto roomInfoRequestDto) {
        travelService.updateRoom(roomInfoRequestDto, roomId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 여행 방 삭제
    @Operation(summary = "여행방 삭제", description = "요청 시, 채팅방 정보를 삭제할 수 있습니다. ")
    @DeleteMapping(value = "/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId) {
        travelService.delete(roomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }










}
