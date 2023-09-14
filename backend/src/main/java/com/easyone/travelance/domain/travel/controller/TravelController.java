package com.easyone.travelance.domain.travel.controller;


import com.easyone.travelance.domain.travel.dto.RoomInfoRequestDto;
import com.easyone.travelance.domain.travel.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("travel/room")
public class TravelController {

    //여행방 전체 리스트


    // 방 만들기(방이 만들어졌다고 보내죠야 하낟...._
    @PostMapping(value = "")
    public ResponseEntity<Void> makeroom(@RequestBody RoomInfoRequestDto roomInfoRequestDto) {
        TravelService.save(roomInfoRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    // 친구 초대

    // 여행 방 수정

    //여행 방 조회

    // 여행 방 삭제

    //예산 대비 소비내역 통계

    // 예산 카테고리 분류 통계







}
