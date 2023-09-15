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
    @PostMapping(value = "")
    @Operation(summary = "여행방 만들기", description = "요청 시, 채팅방을 만듭니다. " +
            "travelName: 여행이름, location: 여행장소, startDate:여행시작일, endDate: 여행종료일, budget: 예산")
    public ResponseEntity<?> makeroom(@RequestBody RoomInfoRequestDto roomInfoRequestDto) {
        travelService.save(roomInfoRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 친구 초대(딥링크)

    //여행방 전체 리스트
    @GetMapping(value = "")
    @Operation(summary = "여행 전체 리스트 조회", description = "요청 시, 채팅방 전체리스트를 조회합니다. " +
            "travelName: 여행이름, location: 여행장소, startDate:여행시작일, endDate: 여행종료일, budget: 예산")
    public ResponseEntity<List<RoomAllResponseDto>> findAllDesc() {
        List<RoomAllResponseDto> responseDtos = travelService.findAllDesc();
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    //여행 방 조회
    @GetMapping(value = "/{roomId}")
    @Operation(summary = "특정 여행방 조회하기", description = "요청 시, 채팅방을 조회합니다. ")
    public ResponseEntity<RoomStaticResponseDto> findById(@PathVariable int roomId) {
        RoomStaticResponseDto responseDto=  travelService.findById(roomId);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }


    // 여행 방 수정
//    @PutMapping(value = "/{roomId}")
//    @Operation(summary = "여행방 만들기", description = "요청 시, 채팅방을 만듭니다. " +
//            "travelName: 여행이름, location: 여행장소, startDate:여행시작일, endDate: 여행종료일, budget: 예산")
//    public ResponseEntity<?> reroom(@RequestBody RoomInfoRequestDto roomInfoRequestDto) {
//        travelService.save(roomInfoRequestDto);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

    // 여행 방 삭제

    //예산 대비 소비내역 통계

    // 예산 카테고리 분류 통계







}
